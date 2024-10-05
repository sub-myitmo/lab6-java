package server.commands;

import server.managers.CollectionManager;
import common.actions.Console;
import common.exceptions.WrongCommandArgsException;
import server.managers.ResponseManager;

/**
 * Команда remove_first - удалить первый элемент из коллекции
 *
 * @author petrovviacheslav
 */
public class RemoveFirst extends Command {
    /**
     * Менеджер коллекции
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса RemoveFirst
     *
     * @param collectionManager менеджер коллекции
     */
    public RemoveFirst(CollectionManager collectionManager) {
        super("remove_first", "удалить первый элемент из коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Удаляет первый элемент из коллекции
     *
     * @return true - команда выполнена успешно, иначе false
     */
    @Override
    public boolean execute(String arg, Object otherArg) {
        try {
            if (!arg.isEmpty() || otherArg != null) throw new WrongCommandArgsException();
            collectionManager.removeFirstElementCollection();
            ResponseManager.addln("Первая группа была успешно удалена");
            return true;
        } catch (ArrayIndexOutOfBoundsException | WrongCommandArgsException e) {
            ResponseManager.addln(e.toString());
            return false;
        }
    }
}
