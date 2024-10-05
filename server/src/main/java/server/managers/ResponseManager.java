package server.managers;

public class ResponseManager {
    private static StringBuilder string = new StringBuilder();

    public static String getString() {
        return string.toString();
    }

    // названо по аналогии со стеком
    public static String popString() {
        String stringReturn = string.toString();
        string.delete(0, string.length());

        return stringReturn;
    }
    public static void addln(Object object) {
        string.append(object).append("\n");
    }

    public static void addlnTwoArgs(Object object1, Object object2) {
        string.append(object1).append(" - ").append(object2).append("\n");
    }

}
