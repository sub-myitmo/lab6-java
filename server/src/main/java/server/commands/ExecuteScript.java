package server.commands;

import common.actions.Console;
import common.actions.UserResponse;
import common.exceptions.WrongCommandArgsException;
import server.managers.ResponseManager;

import java.util.Scanner;
import java.util.Stack;

/**
 * Команда clear - выполняет скрипт из файла
 *
 * @author petrovviacheslav
 */

public class ExecuteScript extends Command {

    boolean flag = true;
    static Stack<String> stackFiles = new Stack<>();
    static Stack<Scanner> stackScanners = new Stack<>();
    /**
     * Конструктор ExecuteScript
     */
    public ExecuteScript() {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
    }

    /**
     * Выполняет скрипт из файла
     *
     * @return true - команда выполнена успешно, иначе false
     */
    @Override
    public boolean execute(String arg, Object otherArg) {
        try {
            if (arg.isEmpty() || otherArg != null) throw new WrongCommandArgsException();
            ResponseManager.addln("Скрипт выполняется");
            return true;
        } catch (WrongCommandArgsException e) {
            ResponseManager.addln(e.toString());
            return false;
        }
    }
}
