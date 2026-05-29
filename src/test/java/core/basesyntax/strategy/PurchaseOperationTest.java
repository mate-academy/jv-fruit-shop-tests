package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.strategy.PurchaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private PurchaseOperation po;

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
        po = new PurchaseOperation();
    }

    @Test
    void purchaseOperation_ok() {
        String banana = "banana";
        Storage.getStorage().put(banana, 50);
        FruitTransaction ft = new FruitTransaction(banana, Operation.PURCHASE, 25);
        po.apply(ft);
        int actual = Storage.getStorage().get(banana);
        assertEquals(25, actual);
    }

    @Test
    void nouEnoughtFruits_notOk() {
        String apple = "apple";
        Storage.getStorage().put(apple, 25);
        FruitTransaction ft = new FruitTransaction(apple, Operation.PURCHASE, 50);
        assertThrows(RuntimeException.class, () -> {
            po.apply(ft);
        });
    }
}
