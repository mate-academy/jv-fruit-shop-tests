package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.ReturnOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    @AfterEach
    void tearDown() {
        Storage.fruitModels.clear();
    }

    @Test
    void handle_existingFruitReturn_ok() {
        Storage.fruitModels.put("banana", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 100
        );
        OperationHandler operationHandler = new ReturnOperation();
        operationHandler.handle(fruitTransaction);
        Assertions.assertEquals(200,Storage.fruitModels.get("banana"));
    }

    @Test
    void handle_newFruitReturn_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 100
        );
        OperationHandler operationHandler = new ReturnOperation();
        operationHandler.handle(transaction);
        Assertions.assertEquals(100, Storage.fruitModels.get("banana"));
    }
}
