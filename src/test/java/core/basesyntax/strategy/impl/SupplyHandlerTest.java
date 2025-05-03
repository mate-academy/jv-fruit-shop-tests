package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

public class SupplyHandlerTest {

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
    }

    @Test
    public void executeOperationSupplyTransaction_ok() {
        OperationHandler supplyHandler = new SupplyHandler();
        int quantityOfFruit = 100;
        String fruit = "banana";
        FruitTransaction transactionSupply =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, fruit, quantityOfFruit);
        Storage.storage.put(transactionSupply.getFruit(),50);
        supplyHandler.executeOperation(transactionSupply);
        int expectedQuantity = 150;
        int actualQuantity = Storage.storage.get(transactionSupply.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void executeOperationSupplyTransactionNegativeQuantity_Notok() {
        OperationHandler supplyHandler = new SupplyHandler();
        int quantityOfFruit = -20;
        String fruit = "banana";
        FruitTransaction transactionSupply =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, fruit, quantityOfFruit);
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                supplyHandler.executeOperation(transactionSupply));
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }
}
