package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.operation.ReturnOperationHandler;
import core.basesyntax.strorage.FruitStorage;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnHandler;

    @BeforeClass
    public static void setUp() {
        returnHandler = new ReturnOperationHandler();
    }

    @Test
    public void apply_validCase_ok() {
        FruitStorage.fruits.put("apple", 50);
        returnHandler.apply(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10));
        int expected = 60;
        int actual = FruitStorage.fruits.get("apple");
        assertEquals("Buyer return some fruits, balance: ", expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullValue_notOk() {
        returnHandler.apply(null);
    }
}
