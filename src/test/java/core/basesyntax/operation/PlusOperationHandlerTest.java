package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.NegativeNumberException;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PlusOperationHandler;
import org.junit.jupiter.api.Test;

public class PlusOperationHandlerTest {
    private final OperationHandler operationHandler = new PlusOperationHandler();

    @Test
    public void getHandle_valid_Ok() {
        Integer firstInt = 5;
        Integer secondInt = 5;

        assertEquals(10, operationHandler.handle(firstInt, secondInt));
    }

    @Test
    public void getHandle_notValid_notOk() {
        Integer firstInt = 5;
        Integer secondInt = -10;

        assertThrows(NegativeNumberException.class, () ->
                operationHandler.handle(firstInt,secondInt));
        assertThrows(NegativeNumberException.class, () ->
                operationHandler.handle(secondInt,firstInt));
    }
}
