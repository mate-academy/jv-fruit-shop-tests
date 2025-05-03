package core.basesyntax.service.operationhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.ShopDataBase;
import core.basesyntax.service.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private OperationHandler balanceHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceOperation();
    }

    @Test
    void testBalanceSetsInitialQuantity_Ok() {
        FruitTransaction transaction = new FruitTransaction("b", "apple", 100);
        balanceHandler.process(transaction);
        assertEquals(100, ShopDataBase.shopData.get("apple"));
    }

    @Test
    void testBalanceOverwritesExistingQuantity_Ok() {
        ShopDataBase.shopData.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction("b", "banana", 75);
        balanceHandler.process(transaction);
        assertEquals(75, ShopDataBase.shopData.get("banana"));
    }

    @AfterEach
    void tearDown() {
        ShopDataBase.shopData.clear();
    }
}
