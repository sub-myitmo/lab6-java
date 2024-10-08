package server.commands;

import server.managers.CollectionManager;
import common.actions.Console;
import common.exceptions.WrongCommandArgsException;
import common.models.StudyGroup;
import server.managers.ResponseManager;

import java.util.Collections;
import java.util.Stack;

/**
 * Команда print_ascending - вывести элементы коллекции в порядке возрастания
 *
 * @author petrovviacheslav
 */
public class PrintAscending extends Command {
    /**
     * Менеджер коллекции
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса PrintAscending
     *
     * @param collectionManager менеджер коллекции
     */
    public PrintAscending(CollectionManager collectionManager) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String arg, Object otherArg) {
        try {
            if (!arg.isEmpty() || otherArg != null) throw new WrongCommandArgsException();
            Stack<StudyGroup> copyOfStackCollection = collectionManager.getStackCollection();
            Collections.sort(copyOfStackCollection);
            for (StudyGroup studyGroup : copyOfStackCollection) {
                ResponseManager.addln(studyGroup.toString());
            }
            return true;
        } catch (WrongCommandArgsException e) {
            ResponseManager.addln(e.toString());
            return false;
        }

    }
}
