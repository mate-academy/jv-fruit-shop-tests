package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final Operation BALANCE = Operation.BALANCE;
    private OperationHandler balanceOperationHandler;

    @Before
    public void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    public void handleBalanceOperation_Ok() {
        FruitTransaction orange = new FruitTransaction();
        orange.setName("orange");
        orange.setQuantity(10);
        orange.setOperation(BALANCE);
        balanceOperationHandler.handle(orange);
        Integer expectedQuantity = 10;
        Integer actualQuantity = Storage.fruits.get("orange");
        assertEquals("Test failed! Expected quantity should be " + expectedQuantity + " but it is "
                        + actualQuantity, expectedQuantity, actualQuantity);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
