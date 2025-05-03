package core.basesyntax.model;

import core.basesyntax.ValidationException;
import java.util.Objects;

public class Fruit {
    private String name;

    public Fruit(String name) {
        if (name == null) {
            throw new ValidationException("Name cannot be null");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fruit fruit = (Fruit) o;
        return Objects.equals(name, fruit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Fruit{"
                + "name='" + name + '\'' + '}';
    }
}
