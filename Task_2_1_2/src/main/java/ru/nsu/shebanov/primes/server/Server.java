package ru.nsu.shebanov.primes.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.shebanov.primes.common.KnownPrimes;
import ru.nsu.shebanov.primes.common.Submission;
import ru.nsu.shebanov.primes.common.Task;

public class Server {
    public final int port;
    public final Selector selector;
    public final ServerSocketChannel serverChannel;
    public final List<Socket> clients;
    public final List<Integer> checkTask;

    public Server(int port, List<Integer> checkTask) throws IOException {
        this.port = port;
        this.checkTask = checkTask;


        this.selector = Selector.open();
        this.clients = new ArrayList<>();

        this.serverChannel = ServerSocketChannel.open();
        InetSocketAddress localAddress = (InetSocketAddress) serverChannel.getLocalAddress();
        String ip = localAddress.getAddress().getHostAddress();
        this.serverChannel.bind(new InetSocketAddress(port));
        this.serverChannel.configureBlocking(false);
        this.serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.printf("Started on ip: %s \n", ip);
    }

    public void run() throws IOException {
        KnownPrimes knownPrimes = new KnownPrimes();
        CheckResult checkResult = new CheckResult();
        TaskDistributor taskDistributor = new TaskDistributor(knownPrimes);

        Thread checkerThread = new Thread(new CheckerThread(checkTask, knownPrimes, checkResult));
        checkerThread.start();

        while (checkerThread.isAlive()) {
            selector.select();
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

            while (keyIterator.hasNext() && checkerThread.isAlive()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();

                if (key.isAcceptable()) {
                    ServerSocketChannel srv = (ServerSocketChannel) key.channel();
                    SocketChannel clientChannel = srv.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);

                    taskDistributor.addWorker();

                    Task task = taskDistributor.getTask();
                    ObjectMapper mapper = new ObjectMapper();
                    byte[] data = mapper.writeValueAsBytes(task);
                    ByteBuffer buffer = ByteBuffer.wrap(data);
                    clientChannel.write(buffer);

                    System.out.println("Client created");
                }

                if (key.isReadable()) {
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int bytesRead = clientChannel.read(buffer);

                    if (bytesRead == -1) {
                        System.out.println("Client destroyed");
                        taskDistributor.removeWorker();
                        clientChannel.close();
                        continue;
                    }

                    buffer.flip();
                    byte[] data = new byte[buffer.limit()];
                    buffer.get(data);

                    ObjectMapper mapper = new ObjectMapper();
                    Submission submission = mapper.readValue(data, Submission.class);
                    taskDistributor.submitTask(submission);
                }
            }

            System.out.println(checkResult.foundPrime);
        }
    }
}
