package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void setUp() {
        operationHandler = new BalanceOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitModels.clear();
    }

    @Test
    void handle_newFruitBalance_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100
        );
        operationHandler.handle(fruitTransaction);
        assertEquals(100, Storage.fruitModels.get("apple"));
    }

    @Test
    void handle_existingFruitBalance_ok() {
        Storage.fruitModels.put("apple", 50);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100
        );
        operationHandler.handle(fruitTransaction);
        assertEquals(100, Storage.fruitModels.get("apple"));
    }
}
