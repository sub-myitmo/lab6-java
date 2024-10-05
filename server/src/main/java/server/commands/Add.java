package server.commands;


import common.actions.GroupMask;
import common.exceptions.WrongCommandArgsException;
import common.models.StudyGroup;
import server.managers.CollectionManager;
import server.managers.ResponseManager;

import java.util.Date;


/**
 * Команда add - добавить новый элемент в коллекцию
 *
 * @author petrovviacheslav
 */
public class Add extends Command {
    /**
     * Менеджер коллекции
     */
    private CollectionManager collectionManager;

    /**
     * Конструктор класса Add
     *
     * @param collectionManager менеджер коллекции
     */
    public Add(CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String arg, Object otherArg) {
        try {
            if (!arg.isEmpty() || otherArg == null) throw new WrongCommandArgsException();

            GroupMask group = (GroupMask) otherArg;
            collectionManager.addElementToCollection(new StudyGroup(group.getName(), group.getCoordinates(), new Date(), group.getStudentsCount(), group.getExpelledStudents(), group.getTransferredStudents(), group.getSemesterEnum(), group.getGroupAdmin()));

            ResponseManager.addln("Группа была создана успешно");
            return true;

        } catch (WrongCommandArgsException /*| IncorrectScriptException*/ e) {
            ResponseManager.addln(e.toString());
            return false;
        }
    }
}
