package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.StorageService;
import org.junit.After;
import org.junit.Test;

public class StorageServiceSupplyImplTest {
    private StorageService storageServiceSupply = new StorageServiceSupplyImpl(new FruitDaoImpl());

    @After
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void supplyFruitQuantity() {
        Fruit fruit = new Fruit();
        fruit.setNameFruit("pineapple");
        fruit.setQuantityFruit(1900);
        Storage.fruits.add(fruit);
        storageServiceSupply.operateSupply("pineapple",1000);
        Integer quantity = Storage.fruits.get(0).getQuantityFruit();
        assertEquals((int) quantity,2900);
    }
}
