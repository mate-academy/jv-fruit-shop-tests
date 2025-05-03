package core.basesyntax.model;

public class Fruit {
    private final String fruitType;
    private int fruitQuantity;

    public Fruit(String fruitType, int fruitQuantity) {
        this.fruitType = fruitType;
        this.fruitQuantity = fruitQuantity;
    }

    public String getFruitType() {
        return fruitType;
    }

    public int getFruitQuantity() {
        return fruitQuantity;
    }

    public void setFruitQuantity(int fruitQuantity) {
        this.fruitQuantity = fruitQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o.getClass() == Fruit.class && o.hashCode() == this.hashCode())) {
            return false;
        }
        Fruit fruit = (Fruit) o;
        return fruitQuantity == fruit.fruitQuantity
                && fruit.getFruitType() == null || fruit.getFruitType().equals(this.fruitType);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + fruitQuantity;
        result = 31 * result + (fruitType == null ? 0 : fruitType.hashCode());
        return result;
    }
}
