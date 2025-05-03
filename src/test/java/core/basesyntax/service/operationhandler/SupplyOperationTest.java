package core.basesyntax.service.operationhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.ShopDataBase;
import core.basesyntax.service.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private OperationHandler supplyHandler;

    @BeforeEach
    void setUp() {
        supplyHandler = new SupplyOperation();
    }

    @Test
    void testSupplyAddsToExistingQuantity_Ok() {
        ShopDataBase.shopData.put("apple", 100);
        FruitTransaction transaction = new FruitTransaction("s", "apple", 50);
        supplyHandler.process(transaction);
        assertEquals(150, ShopDataBase.shopData.get("apple"));
    }

    @Test
    void testSupplyCreatesNewEntry_Ok() {
        FruitTransaction transaction = new FruitTransaction("s", "pear", 30);
        supplyHandler.process(transaction);

        assertEquals(30, ShopDataBase.shopData.get("pear"));
    }

    @AfterEach
    void tearDown() {
        ShopDataBase.shopData.clear();
    }

}
