package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitShop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private OperationHandler operationHandler;

    @BeforeEach
    public void setUp() {
        operationHandler = new ReturnOperation();
        FruitShop.storage.put("Apple", 0);
    }

    @Test
    public void returnFruitThatShopDoesntContain_throwsException() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction
                .Operation.RETURN, "Banana", 3);
        assertThrows(RuntimeException.class, () -> operationHandler.process(fruitTransaction));
    }

    @Test
    public void returnFruitThatShopContains_increasesStock() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction
                .Operation.RETURN, "Apple", 3);
        operationHandler.process(fruitTransaction);
        int actual = FruitShop.storage.get("Apple");
        assertEquals(3, actual);
    }

    @AfterEach
    public void tearDown() {
        FruitShop.storage.clear();
    }
}
