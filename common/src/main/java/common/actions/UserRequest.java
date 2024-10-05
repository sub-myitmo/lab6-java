package common.actions;

import java.io.File;
import java.io.Serializable;

public class UserRequest implements Serializable {
    private final String name;
    private final String stringArgument;
    private final Serializable otherArguments;

    public UserRequest(String name, String stringArgument, Serializable otherArguments) {
        this.name = name;
        this.stringArgument = stringArgument;
        this.otherArguments = otherArguments;
    }

    public UserRequest(String name, String stringArgument) {
        this(name, stringArgument, null);
    }
    public UserRequest(File file) {
        this("","", file);
    }

    public UserRequest() {
        this("", "");
    }

    /**
     * @return Command name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Command string argument.
     */
    public String getStringArgument() {
        return stringArgument;
    }

    /**
     *
     */
    public Object getOtherArguments() {
        return otherArguments;
    }

    /**
     * @
     */
    public boolean isEmpty() {
        return name.isEmpty() && stringArgument.isEmpty() && otherArguments == null;
    }

    @Override
    public String toString() {
        return "Request[" + name + ", " + stringArgument + ", " + otherArguments + "]";
    }
}
