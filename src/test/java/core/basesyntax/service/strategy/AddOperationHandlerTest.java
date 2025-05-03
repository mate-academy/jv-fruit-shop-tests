package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    public static final Transaction VALID_BALANCE =
            new Transaction(Operation.get("b"), new Fruit("apple"), 20);
    public static final Transaction VALID_ADD =
            new Transaction(Operation.get("s"), new Fruit("apple"), 100);
    public static final Integer BALANCE_QUANTITY = 20;
    public static final Integer ADD_QUANTITY = 120;
    private static OperationHandler addOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        addOperationHandler = new AddOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.clear();
    }

    @Test
    public void correctBalanceQuantity_Ok() {
        addOperationHandler.execute(VALID_BALANCE);
        Fruit fruit = new Fruit(VALID_BALANCE.getFruit().getName());
        Integer actual = Storage.storage.get(fruit);
        assertEquals(BALANCE_QUANTITY,
                actual);
    }

    @Test
    public void correctAddQuantity_Ok() {
        Fruit fruit = new Fruit(VALID_ADD.getFruit().getName());
        Storage.storage.put(fruit, BALANCE_QUANTITY);
        addOperationHandler.execute(VALID_ADD);
        Integer actual = Storage.storage.get(fruit);
        assertEquals(ADD_QUANTITY,
                actual);
    }
}
