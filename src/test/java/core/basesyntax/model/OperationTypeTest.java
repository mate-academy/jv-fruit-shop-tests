package core.basesyntax.model;

import exception.OperationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationTypeTest {
    @Test
    void operationType_Ok() {
        final String actual = OperationType.getOperationType("b").name();
        System.out.println(actual);
        Assertions.assertEquals("BALANCE", actual);
    }

    @Test
    void operationType_NotOk() {
        Assertions.assertThrows(OperationException.class, () -> {
            OperationType.getOperationType("n").name(); },
                "NumberFormatException error was expected");
    }
}
