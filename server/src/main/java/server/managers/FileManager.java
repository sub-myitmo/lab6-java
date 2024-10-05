package server.managers;


import common.exceptions.*;
import common.actions.Console;
import java.io.*;


/**
 * Класс FileManager для записи коллекции в файл и чтение из файла
 */
public class FileManager {
    /**
     * Переменная хранящая путь до начального файла .json
     */
    public static String path;
    private final File file;

    /**
     * Конструктор класса CreateSemester
     *
     */
    public FileManager(File file) {
        this.file = file;
    }

    /**
     * Чтение текста из файла
     *
     * @return String текст из файла
     */
    public String readFromFile() {
        var filePath = file;
        InputStreamReader inputStreamReader;
        try {

            if (!filePath.canRead()) throw new NotEnoughRightsReadException();
            if (!filePath.canWrite()) Console.println("Внимание! Вы не сможете использовать команду save!");

            inputStreamReader = new InputStreamReader(new FileInputStream(filePath));
            StringBuilder stringFile = new StringBuilder();
            int symbolNow = inputStreamReader.read();
            while (symbolNow != -1) {
                stringFile.append(((char) symbolNow));
                symbolNow = inputStreamReader.read();
            }
            inputStreamReader.close();
            return stringFile.toString();
        } catch (NotEnoughRightsReadException e) {
            Console.printerror(e + " Коллекция пуста!");
            return "";
        } catch (IOException e) {
            Console.printerror("Json-файл не найден. Коллекция пуста!");
            return "";
        }
    }

    /**
     * Запись текста в файл
     *
     * @param text     текст для файла
     */
    public void writeToFile(String text) {
        try {
            var filePath = file;
            if (!filePath.canWrite()) throw new NotEnoughRightsWriteException();

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(filePath));
            char[] chars = text.toCharArray();
            outputStreamWriter.write(chars, 0, chars.length);
            Console.println("Коллекция была успешно сохранена");
            outputStreamWriter.close();
        } catch (NotEnoughRightsWriteException e) {
            Console.println(e.toString());
        } catch (IOException e) {
            Console.println("ошибка при записи файла!");
        }
    }

    /**
     * Метод для получения имени файла из переменной окружения
     *
     * @return имя файла
     */
    public static String getName() {
        path = System.getenv("LAB5_DATA");
        return path;
    }
}