package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitShop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private OperationHandler operationHandler;

    @BeforeEach
    public void setup() {
        operationHandler = new SupplyOperation();
        FruitShop.storage.put("Apple", 10);
    }

    @Test
    public void supplyUnknownFruit_addsToStorage() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, "Coconut", 3);
        operationHandler.process(transaction);
        int actual = FruitShop.storage.get("Coconut");
        assertEquals(3, actual);
    }

    @Test
    public void supplyExitingFruit_increasesStock() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, "Apple", 3);
        operationHandler.process(transaction);
        int actual = FruitShop.storage.get("Apple");
        assertEquals(13, actual);
    }

    @AfterEach
    public void tearDown() {
        FruitShop.storage.clear();
    }
}
