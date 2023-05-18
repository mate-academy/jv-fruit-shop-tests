package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SubtractionOperationTest {
    private static OperationHendler operationHendler;
    private static final String MASSAGE = "Should throw runtime exception.";

    @BeforeAll
    public static void init() {
        operationHendler = new SubtractionOperation();
    }

    @Test
    void additional_null_notOk() {
        assertThrows(RuntimeException.class,
                () -> operationHendler.operateTransaction(null),
                MASSAGE);
    }
}
