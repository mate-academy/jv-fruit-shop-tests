package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static Fruit apple;
    private static Fruit banana;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceOperationHandler();
        apple = new Fruit("apple");
        banana = new Fruit("banana");
        Storage.storage.put(apple, 100);
    }

    @Test
    public void apply_balanceOperationHandlerNewFruit_Ok() {
        Transaction newFruitTransaction = new Transaction(Operation.BALANCE,
                banana, 100);
        operationHandler.apply(newFruitTransaction);
        Map<Fruit,Integer> expected = new HashMap<>();
        expected.put(apple, 100);
        expected.put(banana, 100);
        assertEquals(expected, Storage.storage);
    }

    @Test
    public void apply_balanceOperationHandlerFruitExist_Ok() {
        operationHandler.apply(new Transaction(Operation.BALANCE, apple, 20));
        Map<Fruit,Integer> expected = new HashMap<>();
        expected.put(apple, 120);
        expected.put(banana, 100);
        assertEquals(expected, Storage.storage);
    }
}
