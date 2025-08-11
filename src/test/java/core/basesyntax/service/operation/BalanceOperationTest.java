package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitShop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private OperationHandler operationHandler;

    @BeforeEach
    public void setUp() {
        operationHandler = new BalanceOperation();
        FruitShop.storage.put("Coconut", 52);
        FruitShop.storage.put("Apple", 42);
    }

    @Test
    public void processExitingFruit_updateQuantity() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "Apple", 44);
        operationHandler.process(fruitTransaction);
        int actual = FruitShop.getQuantity("Apple");
        assertEquals(44, actual);
    }

    @Test
    public void processUnknownFruit_addsToStorage() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "DragonFruit", 8);
        operationHandler.process(fruitTransaction);
        int actual = FruitShop.getQuantity("DragonFruit");
        assertEquals(8, actual);
    }

    @AfterEach
    public void tearDown() {
        FruitShop.storage.clear();
    }
}
