package server;

import common.actions.*;
import common.actions.Console;

import server.managers.*;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Logger;

public class Server {

    private static final Logger logger = Logger.getLogger( Server.class.getName() );

    private int port;
    private Selector selector;
    private DatagramChannel datagramChannel;
    private QueryParser queryParser;

    private FileManager fileManager;
    private CollectionManager collectionManager;
    private ParseManager parseManager;

    public Server(int port, QueryParser queryParser, FileManager fileManager, CollectionManager collectionManager, ParseManager parseManager) throws IOException {
        this.port = port;
        this.queryParser = queryParser;
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
        this.parseManager = parseManager;

        this.selector = Selector.open();
        this.datagramChannel = DatagramChannel.open();
        this.datagramChannel.configureBlocking(false);
        this.datagramChannel.bind(new InetSocketAddress(this.port));
        this.datagramChannel.register(selector, SelectionKey.OP_READ);
    }

    public void connection() {
        logger.info("The server is running");
        new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                try {
                    String stringInput = reader.readLine();
                    if (stringInput.trim().equals("save")) {
                        fileManager.writeToFile(parseManager.getJsonFromStack(collectionManager.getStackCollection()));
                    } else if (stringInput.trim().equals("exit")) {
                        Console.println("Завершение работы сервера ...");
                        System.exit(0);
                    } else {
                        Console.println("Это не команда save/exit!!!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    continue;
                }
            }
        }).start();
        while (true) {
            try {

                if (selector.selectNow() > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            processingRequest(key);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error during I/O operations: " + e.getMessage());
            }

        }
    }

    private void processingRequest(SelectionKey key) {
        DatagramChannel clientCh = (DatagramChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(4096);

        try {
            // Получение данных от клиента
            SocketAddress clientAddress = clientCh.receive(buffer);
            buffer.flip();

            // Десериализация запроса
            ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer.array());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            UserRequest userRequest = (UserRequest) objectInputStream.readObject();

            logger.info("The request has been received");

            // Выдача ответа по запросу
            UserResponse userResponse = queryParser.processing(userRequest);

            logger.info("The response has been created (command - " + userRequest.getName().trim() + ")");

            // Сериализация ответа

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(userResponse);
            byte[] responseData = byteArrayOutputStream.toByteArray();


            // Отправка ответа клиенту
            buffer.clear();
            buffer.put(responseData);
            buffer.flip();
            clientCh.send(buffer, clientAddress);
            logger.info("The response has been serialized and sent (command - " + userRequest.getName().trim() + ")");

        } catch (IOException e) {
            logger.severe("Ошибка с IO потоками");
            Console.println("Ошибка с IO потоками");
        } catch (ClassNotFoundException e) {
            logger.severe("Объект не может быть сериализован");
            Console.println("Объект не может быть сериализован");
        }
    }

}
