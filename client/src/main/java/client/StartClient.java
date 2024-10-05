package client;

import client.helpers.UserHandler;
import common.actions.Console;

import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;
import java.util.logging.Logger;

public class StartClient {
    private static String host = "localhost";

    public static void main(String[] args) {

        try {

            Scanner userScanner = new Scanner(System.in);
            UserHandler userHandler = new UserHandler(userScanner);
            Client client = new Client(host, Integer.parseInt(args[0]), userHandler);

            DatagramChannel datagramChannel = DatagramChannel.open();
            InetSocketAddress address = new InetSocketAddress(host, Integer.parseInt(args[0]));
            datagramChannel.connect(address);

            Console.println("Соединение выполнено, хост: " + host);
            client.run();
            userScanner.close();

        } catch (Exception e){
            Console.println("Возникла ошибка");
        }
    }
}