package core.basesyntax.service.operations;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PurchaseOperationTest {
    private static final int DEFAULT_QUANTITY = 100;
    private FruitTransaction fruitTransaction = new FruitTransaction();
    private PurchaseOperation purchaseOperation = new PurchaseOperation();

    @BeforeEach
    void before() {
        fruitTransaction.setQuantity(DEFAULT_QUANTITY);
    }

    @Test
    void purchaseOperation_Ok() {
        int quantity = 10;
        purchaseOperation.doOperation(fruitTransaction, quantity);
        assertEquals(DEFAULT_QUANTITY - quantity, fruitTransaction.getQuantity());
    }
}