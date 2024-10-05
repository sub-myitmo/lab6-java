package server.commands;

import common.actions.Console;
import common.exceptions.WrongCommandArgsException;
import server.managers.ResponseManager;

/**
 * Команда exit - завершить программу (без сохранения в файл)
 *
 * @author petrovviacheslav
 */
public class Exit extends Command {


    /**
     * Конструктор класса Exit
     */
    public Exit() {
        super("exit", "завершить программу (без сохранения в файл)");
    }


    @Override
    public boolean execute(String arg, Object otherArg) {
        try {
            if (!arg.isEmpty() || otherArg != null) throw new WrongCommandArgsException();

            ResponseManager.addln("Завершение выполнения");
            System.exit(0);

            return true;
        } catch (WrongCommandArgsException e) {
            ResponseManager.addln(e.toString());
            return false;
        }

    }
}
