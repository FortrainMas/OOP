package ru.nsu.shebanov.primes.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.shebanov.primes.common.PrimesDelta;
import ru.nsu.shebanov.primes.common.Submission;
import ru.nsu.shebanov.primes.common.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Set;

public class Client {

    public final String host;
    public final int port;
    public final SocketChannel client;

    public Client(String host, int port) throws IOException {
        this.host = host;
        this.port = port;

        InetSocketAddress address = new InetSocketAddress(host, port);
        client = SocketChannel.open();
        client.connect(address);
    }

    public void run() {
        ObjectMapper mapper = new ObjectMapper();
        ByteBuffer buffer = ByteBuffer.allocate(4096);

        try {
            while (client.isOpen()) {
                buffer.clear();
                int bytesRead = client.read(buffer);
                if (bytesRead == -1) {
                    System.out.println("Server closed");
                    break;
                }

                buffer.flip();
                byte[] data = new byte[buffer.limit()];
                buffer.get(data);

                Task task = mapper.readValue(data, Task.class);
                System.out.println("New Task:");
                System.out.println("min: " + task.minimum);
                System.out.println("max: " + task.maximum);
                System.out.println("step: " + task.step);
                System.out.println("workers: " + task.numWorkers);
                System.out.println("known primes: " + task.knownPrimes.primes);

                Set<Integer> primes = new HashSet<>();
                for (int i = task.minimum; i < task.maximum; i++) {
                    boolean isPrime = true;
                    for(var knownPrime : task.knownPrimes.primes) {
                        if(i % knownPrime == 0) {
                            isPrime = false;
                            break;
                        }
                    }
                    if (!isPrime) {
                        break;
                    }

                    for(int j = task.knownPrimes.maximum; j < Math.sqrt(i) + 1; i++) {
                        if (i % j == 0) {
                            isPrime = false;
                            break;
                        }
                    }

                    if(isPrime) {
                        primes.add(i);
                    }
                }

                Submission submission = new Submission(task.minimum, task.maximum, task.step, task.numWorkers, primes);
                byte[] jsonData = mapper.writeValueAsBytes(submission);
                buffer = ByteBuffer.wrap(jsonData);
                while (buffer.hasRemaining()) {
                    client.write(buffer);
                }
            }
        } catch (IOException e) {
            System.err.println("Exception during reading or writing data: " + e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException ignored) {}
        }
    }
}
