package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.NegativeNumberException;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PlusOperationHandler;
import org.junit.jupiter.api.Test;

public class PlusOperationHandlerTest {

    @Test
    public void getHandle_valid_Ok() {
        Integer firstInt = 5;
        Integer secondInt = 5;

        OperationHandler operationHandler = new PlusOperationHandler();
        assertEquals(10, operationHandler.handle(firstInt, secondInt));
    }

    @Test
    public void getHandle_Notvalid_notOk() {
        Integer firstInt = 5;
        Integer secondInt = -10;

        OperationHandler operationHandler = new PlusOperationHandler();
        assertThrows(NegativeNumberException.class, () ->
                operationHandler.handle(firstInt,secondInt));
        assertThrows(NegativeNumberException.class, () ->
                operationHandler.handle(secondInt,firstInt));
    }
}
