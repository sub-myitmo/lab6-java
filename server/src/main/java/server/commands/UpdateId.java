package server.commands;

import common.actions.GroupMask;
import server.managers.CollectionManager;
import common.exceptions.*;
import server.managers.ResponseManager;
import common.models.StudyGroup;

import java.util.Date;

/**
 * Команда update id - обновить значение элемента коллекции, id которого равен заданному
 *
 * @author petrovviacheslav
 */
public class UpdateId extends Command {
    /**
     * Менеджер коллекции
     */
    private final CollectionManager collectionManager;


    /**
     * Конструктор класса Show
     *
     * @param collectionManager менеджер коллекции
     */
    public UpdateId(CollectionManager collectionManager) {
        super("update id {element}", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }


    @Override
    public boolean execute(String arg, Object otherArg) {
        try {
            if (arg.isEmpty() || otherArg == null) throw new WrongCommandArgsException();

            GroupMask newGroup = (GroupMask) otherArg;

            long id = Long.parseLong(arg.trim());
            StudyGroup studyGroup = collectionManager.getById(id);
            if (studyGroup == null) throw new NoExistCollectionException();
            collectionManager.updateElement(studyGroup, new StudyGroup(newGroup.getName(), newGroup.getCoordinates(), new Date(), newGroup.getStudentsCount(), newGroup.getExpelledStudents(), newGroup.getTransferredStudents(), newGroup.getSemesterEnum(), newGroup.getGroupAdmin()));
            ResponseManager.addln("Группа была заменена успешно");
            return true;

        } catch (WrongCommandArgsException | NoExistCollectionException e) {
            ResponseManager.addln(e.toString());
            return false;
        }
    }
}
