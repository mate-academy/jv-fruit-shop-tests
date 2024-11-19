package core.basesyntax.dao;

import java.util.Objects;

public class FruitDao {
    private String name;
    private int quantity;

    public FruitDao(String name, int quantity) {
        this.name = name;
        if (checkQuantity(quantity)) {
            this.quantity = quantity;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (checkQuantity(quantity)) {
            this.quantity = quantity;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this != o) {
            if (o != null && getClass() == o.getClass()) {
                FruitDao fruitsDao = (FruitDao) o;
                return Objects.equals(name, fruitsDao.name);
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private boolean checkQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity should be more then 0!");
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "FruitDao{"
                + "name='"
                + name
                + '\''
                + ", quantity="
                + quantity
                + '}';
    }
}
