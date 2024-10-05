package client;

import client.helpers.UserHandler;
import common.actions.Console;
import common.actions.ResponseCode;
import common.actions.UserRequest;
import common.actions.UserResponse;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;

public class Client {

    private String host;
    private int port;

    private UserHandler userHandler;
    private DatagramChannel datagramChannel = DatagramChannel.open();

    public Client(String host, int port, UserHandler userHandler) throws IOException {
        this.host = host;
        this.port = port;
        this.userHandler = userHandler;
        datagramChannel.configureBlocking(false);
    }

    public void run() {
        try {
            boolean status = true;
            while (status) {
                try {
                    status = requestToServer();
                } catch (Exception exception) {
                    Console.println("Ошибка при работе клиента");
                }
                if (datagramChannel != null) datagramChannel.close();
                Console.println("Работа клиента завершена.");
                System.exit(0);
            }
        } catch (IOException e) {
            Console.println("Возникла ошибка!");
        }

    }

    private boolean requestToServer() {
        UserRequest requestToServer = null;
        UserResponse serverResponse = null;
        do {
            try {
                requestToServer = serverResponse != null ? userHandler.handle(serverResponse.getIsGoodResponse()) :
                        userHandler.handle(null);
                if (requestToServer.isEmpty()) continue;

                ByteArrayOutputStream serverWriter = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(serverWriter);
                objectOutputStream.writeObject(requestToServer);
                byte[] bytes;
                bytes = serverWriter.toByteArray();
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                buffer.put(bytes);
                buffer.flip();
                InetSocketAddress address = new InetSocketAddress(host, port);
                datagramChannel.send(buffer, address);
                if (requestToServer.getName().equals("exit")) {
                    System.exit(0);
                }
                ByteBuffer receiveBuffer = ByteBuffer.allocate(4096);

                long timeout = 5000;
                long start = System.currentTimeMillis();
                while (datagramChannel.receive(receiveBuffer) == null && System.currentTimeMillis() - start < timeout) {
                    Thread.sleep(100);
                }

                if (System.currentTimeMillis() - start >= timeout) {
                    System.out.println("Превышено время ожидания ответа от сервера. Повторите попытку позже");
                    continue;
                }

                receiveBuffer.flip();
                byte[] data = new byte[receiveBuffer.limit()];
                receiveBuffer.get(data);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);

                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Object deserializedObject = objectInputStream.readObject();
                serverResponse = (UserResponse) deserializedObject;
                Console.println(serverResponse.getResponse());
            } catch (NullPointerException e) {
                Console.println("Недопустимый ввод");
                requestToServer = userHandler.handle(serverResponse.getIsGoodResponse());
            } catch (ClassNotFoundException e) {
                Console.println("Ошибка при чтении пакета");
            } catch (IOException e) {
                Console.println("Непредвиденная ошибка при отправке данных");
            } catch (InterruptedException e) {
                Console.println("Прервано ожидание ответа от сервера");
            }
        } while (!requestToServer.getName().equals("exit"));

        return false;
    }

}
