package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.FruitTransaction;
import core.basesyntax.Storage;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static OperationHandler balanceOperation;
    private static Storage storage;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        balanceOperation = new BalanceOperation();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
        storage = new Storage();
    }

    @Test
    void handle_GetBalance_Ok() {
        balanceOperation.handle(fruitTransaction, storage);
        Map<String, Integer> fruits = storage.getAllFruits();

        assertEquals(1, fruits.size());
        assertEquals(20, fruits.get("banana"));
    }
}
