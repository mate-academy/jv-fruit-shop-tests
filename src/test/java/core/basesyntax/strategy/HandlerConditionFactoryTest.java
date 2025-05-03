package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;
import org.junit.BeforeClass;
import org.junit.Test;

public class HandlerConditionFactoryTest {
    private static HandlerConditionFactory handler;

    @BeforeClass
    public static void setUpBeforeClass() {
        handler = new HandlerConditionFactory();
    }

    @Test
    public void getHandler_ok() {
        String existing = "b";
        OperationHandler expected = new OperationHandlerBalance();
        assertEquals(expected.getClass(), handler.getHandler(existing).getClass());
    }

    @Test(expected = NoSuchElementException.class)
    public void getHandler_notExist_notOk() {
        String wrong = "A";
        handler.getHandler(wrong);
    }
}
