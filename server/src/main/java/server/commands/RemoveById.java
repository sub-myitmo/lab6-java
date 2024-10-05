package server.commands;

import common.exceptions.NoExistCollectionException;
import server.managers.CollectionManager;
import common.actions.Console;
import common.exceptions.WrongCommandArgsException;
import server.managers.ResponseManager;

/**
 * Команда remove_by_id - удалить элемент из коллекции по его id
 *
 * @author petrovviacheslav
 */
public class RemoveById extends Command {
    /**
     * Менеджер коллекции
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса RemoveById
     *
     * @param collectionManager менеджер коллекции
     */
    public RemoveById(CollectionManager collectionManager) {
        super("remove_by_id", "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String arg, Object otherArg) {
        try {
            if (arg.isEmpty() || otherArg != null) throw new WrongCommandArgsException();

            long id = Long.parseLong(arg.trim());
            if (collectionManager.getById(id) == null) throw new NoExistCollectionException();
            collectionManager.removeGroup(collectionManager.getById(id));

            ResponseManager.addln("Группа была успешно удалена");
            return true;

        } catch (WrongCommandArgsException | NoExistCollectionException e) {
            ResponseManager.addln(e.toString());
            return false;
        }

    }
}
