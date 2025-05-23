package ru.nsu.shebanov.primes;

import ru.nsu.shebanov.primes.client.Client;
import ru.nsu.shebanov.primes.server.Server;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 1) {
            int port = Integer.parseInt(args[0]);

            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] parts = line.trim().split("\\s+");

            List<Integer> task = new ArrayList<>();
            for (String part : parts) {
                task.add(Integer.parseInt(part));
            }


            Server server = new Server(port, task);
            server.run();
        } else if (args.length == 2) {
            String host = args[0];
            int port = Integer.parseInt(args[1]);

            Client client = new Client(host, port);
            client.run();
        } else {
            System.out.println("Incorrect usage");
        }
    }
}