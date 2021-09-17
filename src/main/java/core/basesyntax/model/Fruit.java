package core.basesyntax.model;

public class Fruit {
    private String fruitname;

    public Fruit(String fruitname) {
        this.fruitname = fruitname;
    }

    public String getFruitName() {
        return fruitname;
    }

    @Override
    public int hashCode() {
        return 31 * 17 + (fruitname == null ? 0 : fruitname.hashCode());
    }

    @Override
    public boolean equals(Object newFruit) {
        if (newFruit != null && newFruit.getClass().equals(Fruit.class)) {
            Fruit castedFruit = (Fruit) newFruit;
            return ((this.fruitname == null && castedFruit.getFruitName() == null)
                    || (this.fruitname != null
                    && this.fruitname.equals(castedFruit.getFruitName())));
        }
        return false;
    }
}
