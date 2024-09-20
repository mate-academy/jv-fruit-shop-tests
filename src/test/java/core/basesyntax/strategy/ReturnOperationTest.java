package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.FruitTransaction;
import core.basesyntax.Storage;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static OperationHandler returnOperation;
    private static Storage storage;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        returnOperation = new BalanceOperation();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 20);
        storage = new Storage();
    }

    @Test
    void handle_Return_Ok() {
        returnOperation.handle(fruitTransaction, storage);
        Map<String, Integer> fruits = storage.getAllFruits();

        assertEquals(1, fruits.size());
        assertEquals(20, fruits.get("banana"));
    }
}
