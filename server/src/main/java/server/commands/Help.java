package server.commands;

import server.managers.CommandManager;
import common.actions.Console;
import common.exceptions.WrongCommandArgsException;
import server.managers.ResponseManager;

/**
 * Команда help - вывести справку по доступным командам
 *
 * @author petrovviacheslav
 */
public class Help extends Command {
    /**
     * Менеджер команд
     */
    private final CommandManager commandManager;

    /**
     * Конструктор класса Help
     *
     * @param commandManager менеджер команд
     */
    public Help(CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    @Override
    public boolean execute(String arg, Object otherArg) {
        try {
            if (!arg.isEmpty() || otherArg != null) throw new WrongCommandArgsException();
            commandManager.getCommands().values().forEach(command -> ResponseManager.addlnTwoArgs(command.getName(), command.getDescription()));
            return true;
        } catch (WrongCommandArgsException e) {
            ResponseManager.addln(e.toString());
            return false;
        }

    }
}