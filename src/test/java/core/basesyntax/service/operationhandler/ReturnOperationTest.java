package core.basesyntax.service.operationhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.ShopDataBase;
import core.basesyntax.service.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private OperationHandler returnHandler;

    @BeforeEach
    void setUp() {
        returnHandler = new ReturnOperation();
    }

    @Test
    void testReturnAddsToExistingQuantity_Ok() {
        ShopDataBase.shopData.put("apple", 50);
        FruitTransaction transaction = new FruitTransaction("r", "apple", 10);
        returnHandler.process(transaction);
        assertEquals(60, ShopDataBase.shopData.get("apple"));
    }

    @Test
    void testReturnNonExistingFruit_NotOk() {
        FruitTransaction transaction = new FruitTransaction("r", "mango", 15);
        assertThrows(IllegalArgumentException.class,() -> returnHandler.process(transaction));
    }

    @AfterEach
    void tearDown() {
        ShopDataBase.shopData.clear();
    }

}
