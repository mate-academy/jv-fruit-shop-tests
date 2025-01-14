package core.basesyntax.services.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.ShopService;
import core.basesyntax.storage.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private Storage storage = new Storage();
    private ShopService shopService;
    private FruitTransaction fruitTransaction;
    private Map<String, Integer> testMap;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(storage);
        fruitTransaction = new FruitTransaction();
        testMap = new HashMap<>();
        testMap.put("banana", 152);
        testMap.put("apple", 90);
    }

    @Test
    void check_validShopServiceMethod_ok() {
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setFruit("banana");
        fruitTransaction1.setQuantity(152);

        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setQuantity(90);

        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(fruitTransaction1);
        fruitTransactions.add(fruitTransaction2);

        shopService.operations(fruitTransactions);

        Assertions.assertEquals(testMap.get("banana"), storage.getStorage().get("banana"));
        Assertions.assertEquals(testMap.get("apple"), storage.getStorage().get("apple"));
    }
}
