package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private static PurchaseOperation purchaseOperation;
    private static Integer properQuantity;
    private static Integer corruptedQuantity;

    @BeforeAll
    static void init() {
        purchaseOperation = new PurchaseOperation();
        properQuantity = 35;
        corruptedQuantity = null;
    }

    @Test
    void purchase_properQuantity_ok() {
        Assertions.assertEquals(-properQuantity, purchaseOperation.handle(properQuantity));
    }

    @Test
    void purchase_corruptedQuantity_ok() {
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseOperation.handle(corruptedQuantity));
    }
}
