package core.basesyntax.db;

import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Storage {
    private static final List<Fruit> fruits = new ArrayList<>();

    public static List<Fruit> getFruits() {
        return fruits;
    }

    public static void addFruit(Fruit fruit) {
        fruits.add(fruit);
    }

    public static void removeFruit(String fruitName) {
        for (Fruit fruit : fruits) {
               if (fruit.getName().equals(fruitName)) {
                   fruits.remove(fruit);
                   break;
               }
        throw new NoSuchElementException("Found no fruits with matching name to remove");
        }
    }
}
