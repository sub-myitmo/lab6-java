package server.managers;

import common.actions.ResponseCode;
import common.actions.UserRequest;
import common.actions.UserResponse;
import common.exceptions.WrongCommandArgsException;
import server.commands.Command;

import java.util.Map;


/*
 * Парсер запросов
 * */
public class QueryParser {
    private String name;
    private String stringArgument;
    private final CommandManager commandManager;

    public QueryParser(CommandManager commandManager) {
        this.commandManager = commandManager;
    }


    public UserResponse processing(UserRequest userRequest) {
        name = userRequest.getName().trim();
        stringArgument = userRequest.getStringArgument().trim();

        try {
            Map<String, Command> commands = commandManager.getCommands();
            if (!commands.containsKey(name)) throw new WrongCommandArgsException();

            commands.get(name).execute(stringArgument, userRequest.getOtherArguments());


        } catch (WrongCommandArgsException e) {
            return null;
        }
        return new UserResponse(ResponseCode.OK, ResponseManager.popString());
    }
}
