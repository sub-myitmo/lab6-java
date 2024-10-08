package server.commands;

import server.managers.CollectionManager;
import common.actions.Console;
import common.exceptions.WrongCommandArgsException;
import server.managers.ResponseManager;

/**
 * Команда info - вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
 *
 * @author petrovviacheslav
 */
public class Info extends Command {

    /**
     * Менеджер коллекции
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса Info
     *
     * @param collectionManager менеджер коллекции
     */
    public Info(CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.collectionManager = collectionManager;
    }


    @Override
    public boolean execute(String arg, Object otherArg) {
        try {
            if (!arg.isEmpty() || otherArg != null) throw new WrongCommandArgsException();
            ResponseManager.addln(collectionManager.infoAboutCollection());
            return true;
        } catch (WrongCommandArgsException e) {
            ResponseManager.addln(e.toString());
            return false;
        }
    }
}