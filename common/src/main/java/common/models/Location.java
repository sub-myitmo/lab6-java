package common.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс местоположения человека
 *
 * @author petrovviacheslav
 */

public class Location implements Validatable, Serializable {
    private static final long serialVersionUID = 428728875756667723L;
    private final Float x; //Поле не может быть null
    private final Integer y; //Поле не может быть null
    private final double z;

    /**
     * Конструктор класса Location
     *
     * @param x координата x
     * @param y координата y
     * @param z координата z
     */
    public Location(Float x, Integer y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Получить координату x
     *
     * @return координата x
     */
    public Float getX() {
        return x;
    }

    /**
     * Получить координату y
     *
     * @return координата y
     */
    public Integer getY() {
        return y;
    }

    /**
     * Получить координату z
     *
     * @return координата z
     */
    public double getZ() {
        return y;
    }

    @Override
    public boolean validate() {
        return y != null && x != null;
    }

    @Override
    public String toString() {
        return "Location{" + "x=" + x + ", y=" + y + ", z=" + z + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(x, location.x) && Objects.equals(y, location.y) && Objects.equals(z, location.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}