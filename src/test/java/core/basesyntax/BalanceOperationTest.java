package core.basesyntax;

import core.basesyntax.model.BalanceOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static BalanceOperation balanceOperation;
    private static Integer properQuantity;
    private static Integer corruptedQuantity;

    @BeforeAll
    static void init() {
        balanceOperation = new BalanceOperation();
        properQuantity = 35;
        corruptedQuantity = null;
    }

    @Test
    void balance_properQuantity_ok() {
        Assertions.assertEquals(properQuantity, balanceOperation.handle(properQuantity));
    }

    @Test
    void balance_corruptedQuantity_ok() {
        Assertions.assertThrows(RuntimeException.class,
                () -> balanceOperation.handle(corruptedQuantity));
    }
}
