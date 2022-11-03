package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;
import org.junit.Test;

public class HandlerConditionFactoryTest {
    private final HandlerConditionFactory handler = new HandlerConditionFactory();

    @Test
    public void getHandler_Ok() {
        String existing = "b";
        OperationHandler expected = new OperationHandlerBalance();
        assertEquals(expected.getClass(), handler.getHandler(existing).getClass());
    }

    @Test(expected = NoSuchElementException.class)
    public void getHandler_NotExist() {
        String wrong = "A";
        handler.getHandler(wrong);
    }
}
