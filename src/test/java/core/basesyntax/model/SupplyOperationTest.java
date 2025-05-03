package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static SupplyOperation supplyOperation;
    private static Integer properQuantity;
    private static Integer corruptedQuantity;

    @BeforeAll
    static void init() {
        supplyOperation = new SupplyOperation();
        properQuantity = 35;
        corruptedQuantity = null;
    }

    @Test
    void supply_properQuantity_ok() {
        Assertions.assertEquals(properQuantity, supplyOperation.handle(properQuantity));
    }

    @Test
    void supply_corruptedQuantity_ok() {
        Assertions.assertThrows(RuntimeException.class,
                () -> supplyOperation.handle(corruptedQuantity));
    }
}
