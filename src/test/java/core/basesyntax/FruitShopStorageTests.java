package core.basesyntax;

import core.basesyntax.db.FruitShopStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.util.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitShopStorageTests {
    private FruitShopStorage fruitShopStorageDefault;

    @BeforeEach
    void setUp() {
        fruitShopStorageDefault = new FruitShopStorage();
        fruitShopStorageDefault.put("banana", 100);
        fruitShopStorageDefault.put("apple", 100);
    }

    @Test
    void fruitShopStorage_setStorage_ok() {
        Map<String, Integer> fruitShopStorageNew = new HashMap<>();
        fruitShopStorageNew.put("lemon", 99);
        fruitShopStorageNew.put("grapes", 101);
        fruitShopStorageDefault.setFruitShopStorage(fruitShopStorageNew);
        Map<String, Integer> fruitShopStorageUpdated = fruitShopStorageDefault
                .getFruitShopStorage();
        Assertions.assertEquals(99, fruitShopStorageUpdated.get("lemon"));
        Assertions.assertEquals(101, fruitShopStorageUpdated.get("grapes"));
    }

    @Test
    void fruitShopStorage_getQuantity_ok() {
        int actual = fruitShopStorageDefault.getQuantity("banana");
        Assertions.assertEquals(100, actual);
    }

    @Test
    void fruitShopStorage_getQuantityNull_ok() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            fruitShopStorageDefault.getQuantity(null);
        });
    }

    @Test
    void fruitShopStorage_getQuantityEmptyString_ok() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            fruitShopStorageDefault.getQuantity("");
        });
    }

    @Test
    void fruitShopStorage_getQuantity_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            fruitShopStorageDefault.getQuantity("lemon");
        });
    }

    @Test
    void fruitShopStorage_setFruit_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, "lemon", 50);
        fruitTransaction.setFruit("apple");
        Assertions.assertEquals("apple", fruitTransaction.getFruit());
    }

    @Test
    void fruitShopStorage_setQuantity_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, "lemon", 50);
        fruitTransaction.setQuantity(30);
        Assertions.assertEquals(30, fruitTransaction.getQuantity());
    }

    @AfterEach
    public void afterEachTest() {
        fruitShopStorageDefault.clear();
    }
}
