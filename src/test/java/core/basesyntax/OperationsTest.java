package core.basesyntax;

import core.basesyntax.model.BalanceOperation;
import core.basesyntax.model.PurchaseOperation;
import core.basesyntax.model.ReturnOperation;
import core.basesyntax.model.SupplyOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationsTest {
    private static BalanceOperation balanceOperation;
    private static SupplyOperation supplyOperation;
    private static ReturnOperation returnOperation;
    private static PurchaseOperation purchaseOperation;
    private static Integer properQuantity;
    private static Integer corruptedQuantity;

    @BeforeAll
    static void init() {
        balanceOperation = new BalanceOperation();
        supplyOperation = new SupplyOperation();
        returnOperation = new ReturnOperation();
        purchaseOperation = new PurchaseOperation();
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

    @Test
    void purchase_properQuantity_ok() {
        Assertions.assertEquals(-properQuantity, purchaseOperation.handle(properQuantity));
    }

    @Test
    void purchase_corruptedQuantity_ok() {
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseOperation.handle(corruptedQuantity));
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
