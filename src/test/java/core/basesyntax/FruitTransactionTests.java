package core.basesyntax;

import core.basesyntax.db.FruitShopStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.util.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionTests {
    private FruitShopStorage fruitShopStorageDefault;
    private FruitTransaction fruitTransactionDefault;

    @BeforeEach
    void setUp() {
        fruitTransactionDefault = new FruitTransaction(Operation.BALANCE, "banana", 100);
        fruitShopStorageDefault = new FruitShopStorage();
        fruitShopStorageDefault.put("banana", 100);
        fruitShopStorageDefault.put("apple", 100);
    }

    @Test
    void setOperation_OperationPurchase_ok() {
        fruitTransactionDefault.setOperation(Operation.PURCHASE);
        Operation actual = fruitTransactionDefault.getOperation();
        Assertions.assertEquals(Operation.PURCHASE, actual);
    }

    @Test
    void setQuantity_changeQuantity_ok() {
        fruitTransactionDefault.setQuantity(120);
        int actual = fruitTransactionDefault.getQuantity();
        Assertions.assertEquals(120, actual);
    }

    @Test
    void setFruit_changeFruit_ok() {
        fruitTransactionDefault.setFruit("apple");
        String actual = fruitTransactionDefault.getFruit();
        Assertions.assertEquals("apple", actual);
    }

    @AfterEach
    void tearDown() {
        fruitShopStorageDefault.clear();
    }
}
