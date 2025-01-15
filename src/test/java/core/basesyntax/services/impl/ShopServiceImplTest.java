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
    private static final int BANANA_QUANTITY = 152;
    private static final int APPLE_QUANTITY = 90;

    private Storage storage;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        shopService = new ShopServiceImpl(storage);
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

        Assertions.assertEquals(BANANA_QUANTITY, storage.getStorage().get("banana"));
        Assertions.assertEquals(APPLE_QUANTITY, storage.getStorage().get("apple"));
    }
}
