package core.basesyntax;

import core.basesyntax.model.ReturnOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private static ReturnOperation returnOperation;
    private static Integer properQuantity;
    private static Integer corruptedQuantity;

    @BeforeAll
    static void init() {
        returnOperation = new ReturnOperation();
        properQuantity = 35;
        corruptedQuantity = null;
    }

    @Test
    void return_properQuantity_ok() {
        Assertions.assertEquals(properQuantity, returnOperation.handle(properQuantity));
    }

    @Test
    void return_corruptedQuantity_ok() {
        Assertions.assertThrows(RuntimeException.class,
                () -> returnOperation.handle(corruptedQuantity));
    }
}
