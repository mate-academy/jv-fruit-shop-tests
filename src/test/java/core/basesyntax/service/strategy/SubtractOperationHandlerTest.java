package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubtractOperationHandlerTest {
    public static final Integer INITIAL_QUANTITY = 100;
    public static final Transaction VALID =
            new Transaction(Operation.get("b"), new Fruit("apple"), 20);
    public static final Transaction LARGER_QUANTITY =
            new Transaction(Operation.get("b"), new Fruit("apple"), 110);
    public static final Integer PURCHASE_QUANTITY = 80;
    private static OperationHandler subtractOperationHandler;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        subtractOperationHandler = new SubtractOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.clear();
        fruit = new Fruit("apple");
        Storage.storage.put(fruit, INITIAL_QUANTITY);
    }

    @Test
    public void correctSubtractQuantity_Ok() {
        subtractOperationHandler.execute(VALID);
        Integer actual = Storage.storage.get(fruit);
        assertEquals(PURCHASE_QUANTITY,
                actual);
    }

    @Test(expected = RuntimeException.class)
    public void largerQuantity_notOk() {
        subtractOperationHandler.execute(LARGER_QUANTITY);
    }
}
