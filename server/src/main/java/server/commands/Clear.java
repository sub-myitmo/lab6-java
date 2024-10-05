package server.commands;


import common.actions.Console;
import common.exceptions.WrongCommandArgsException;
import server.managers.CollectionManager;
import server.managers.ResponseManager;

/**
 * Команда clear - очищает коллекцию
 *
 * @author petrovviacheslav
 */
public class Clear extends Command {

    /**
     * Менеджер коллекции
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса Clear
     *
     * @param collectionManager менеджер коллекции
     */
    public Clear(CollectionManager collectionManager) {
        super("clear", "очищает коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Очищает коллекцию
     *
     * @return true - команда выполнена успешно, иначе false
     */
    @Override
    public boolean execute(String arg, Object otherArg) {
        try {
            if (!arg.isEmpty() || otherArg != null) throw new WrongCommandArgsException();
            collectionManager.clearCollection();
            ResponseManager.addln("Коллекция успешно очищена");
            return true;
        } catch (WrongCommandArgsException e) {
            ResponseManager.addln(e.toString());
            return false;
        }

    }

}