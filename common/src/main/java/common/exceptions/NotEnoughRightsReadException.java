package common.exceptions;

/**
 * Выбрасывается, если возникает проблема с доступом к файлу
 *
 * @author petrovviacheslav
 */
public class NotEnoughRightsReadException extends Exception{
    @Override
    public String toString() {
        return "Не хватает прав для доступа к файлу (чтение)!";
    }
}
