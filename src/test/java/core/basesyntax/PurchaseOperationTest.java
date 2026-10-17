package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    @AfterEach
    void tearDown() {
        Storage.fruitModels.clear();
    }

    @Test
    void handle_validData_ok() {
        Storage.fruitModels.put("banana", 150);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana", 50);
        OperationHandler operationHandler = new PurchaseOperation();
        operationHandler.handle(transaction);
        Assertions.assertEquals(100, Storage.fruitModels.get("banana"));
    }

    @Test
    void handle_validData_notOk() {
        Storage.fruitModels.put("banana", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana", 50);
        OperationHandler operationHandler = new PurchaseOperation();;
        Assertions.assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(transaction);
        });
    }
}
