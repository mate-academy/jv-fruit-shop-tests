package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.SupplyOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void setUp() {
        operationHandler = new SupplyOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitModels.clear();
    }

    @Test
    void handle_existingFruitSupply_ok() {
        Storage.fruitModels.put("apple", 50);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 50);
        operationHandler.handle(fruitTransaction);
        assertEquals(100, Storage.fruitModels.get("apple"));
    }

    @Test
    void handle_newFruitSupply_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "orange", 50);
        operationHandler.handle(fruitTransaction);
        assertEquals(50, Storage.fruitModels.get("orange"));
    }
}
