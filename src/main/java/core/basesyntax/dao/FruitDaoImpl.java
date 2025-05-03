package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;

public class FruitDaoImpl implements FruitDao {

    @Override
    public Fruit get(String fruitName) {
        for (Fruit fruitFromStorage: Storage.getFruits()) {
            if (fruitFromStorage.getName().equals(fruitName)) {
                return fruitFromStorage;
            }
        }
        throw new RuntimeException("We have not fruit " + fruitName);
    }

    @Override
    public void add(String fruitName, int quantity) {
        Storage.getFruits().add(new Fruit.FruitBuilder()
                .setName(fruitName).setQuantity(quantity).build());
    }

    @Override
    public void update(String fruitName,int quantity) {
        for (int i = 0; i < Storage.getFruits().size(); i++) {
            if (Storage.getFruits().get(i).getName().equals(fruitName)) {
                Storage.getFruits().get(i).setQuantity(quantity);
            }
        }
    }
}
