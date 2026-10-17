package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.SupplyOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    @AfterEach
    void tearDown() {
        Storage.fruitModels.clear();
    }

    @Test
    void handle_existingFruitSupply_ok() {
        Storage.fruitModels.put("apple", 50);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 50);
        OperationHandler operationHandler = new SupplyOperation();
        operationHandler.handle(fruitTransaction);
        Assertions.assertEquals(100, Storage.fruitModels.get("apple"));
    }

    @Test
    void handle_newFruitSupply_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "orange", 50);
        OperationHandler operationHandler = new SupplyOperation();
        operationHandler.handle(fruitTransaction);
        Assertions.assertEquals(50, Storage.fruitModels.get("orange"));
    }
}
