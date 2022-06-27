package core.basesyntax.model;

public class Fruit {
    private final Operation operation;
    private final String fruit;
    private int quantity;

    public Fruit(Operation operation, String typeOfFruit, int quantityOfFruit) {
        this.operation = operation;
        this.fruit = typeOfFruit;
        this.quantity = quantityOfFruit;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fruit)) {
            return false;
        }
        Fruit fruit1 = (Fruit) o;
        if (quantity != fruit1.quantity) {
            return false;
        }
        if (operation != fruit1.operation) {
            return false;
        }
        return fruit != null ? fruit.equals(fruit1.fruit) : fruit1.fruit == null;
    }

    @Override
    public int hashCode() {
        int result = operation != null ? operation.hashCode() : 0;
        result = 31 * result + (fruit != null ? fruit.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
    }

    public String getFruitType() {
        return fruit;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
