package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.ReturnOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void setUp() {
        operationHandler = new ReturnOperation();
    }

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
        operationHandler.handle(fruitTransaction);
        assertEquals(200,Storage.fruitModels.get("banana"));
    }

    @Test
    void handle_newFruitReturn_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 100
        );
        operationHandler.handle(transaction);
        assertEquals(100, Storage.fruitModels.get("banana"));
    }
}
