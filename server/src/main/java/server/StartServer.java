package server;

import common.actions.Console;
import server.commands.*;
import server.managers.*;
import common.models.StudyGroup;


import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Главный класс приложения, он запускается в первую очередь
 *
 * @author petrovviacheslav
 */
public class StartServer {

    private static final Logger logger = Logger.getLogger(StartServer.class.getName());

    /**
     * Запуск приложения
     *
     * @param args аргументы запуска
     */
    public static void main(String[] args) {


        try {

            logger.info("Launching the console application...");

            String fileName = FileManager.getName();
            if (fileName == null) {

                logger.severe("The environment variable 'LAB5_DATA' is missing!");
                //Console.println("Отсутствует переменная окружения 'LAB5_DATA'!");
                System.exit(0);
            }
            File file = new File(fileName);

            FileManager fileManager = new FileManager(file);


            ParseManager parseManager = new ParseManager();
            Stack<StudyGroup> startStack = parseManager.getStackFromJson(fileManager.readFromFile());

            CollectionManager collectionManager = new CollectionManager();
            collectionManager.setCollection(startStack);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                fileManager.writeToFile(parseManager.getJsonFromStack(collectionManager.getStackCollection()));

            }));
            /* Нахождение максимального id */
            /**
             * Обновить поле id для верного создания новых StudyGroups
             *
             * @param collectionManager менеджер коллекции
             */
            if (collectionManager.getSizeCollection() != 0) {
                long maxId = collectionManager
                        .getStackCollection()
                        .stream().filter(Objects::nonNull)
                        .map(StudyGroup::getId)
                        .mapToInt(Long::intValue).max().orElse(0);
                StudyGroup.setNextId(maxId + 1);
            }


            CommandManager commandManager = new CommandManager() {{
                addCommand("help", new Help(this));
                addCommand("info", new Info(collectionManager));
                addCommand("show", new Show(collectionManager));
                addCommand("clear", new Clear(collectionManager));
                addCommand("remove_by_id", new RemoveById(collectionManager));
                addCommand("remove_first", new RemoveFirst(collectionManager));
                addCommand("shuffle", new Shuffle(collectionManager));
                addCommand("reorder", new Reorder(collectionManager));
                addCommand("print_ascending", new PrintAscending(collectionManager));
                addCommand("print_field_ascending_students_count", new PrintFieldAscendingStudentsCount(collectionManager));
                addCommand("execute_script", new ExecuteScript());
                addCommand("add", new Add(collectionManager));
                addCommand("update", new UpdateId(collectionManager));
                addCommand("exit", new Exit());
            }};

            QueryParser queryParser = new QueryParser(commandManager);

            Server server = new Server(Integer.parseInt(args[0]), queryParser, fileManager, collectionManager, parseManager);

            server.connection();


        } catch (IOException e) {
            e.printStackTrace();
            logger.severe("Ошибка при работе с сокетом");
        } catch (Exception e) {
            e.printStackTrace();
            Console.println("Возникла ошибка");

            logger.severe("Произошла неожиданная ошибка");
        }


    }
}