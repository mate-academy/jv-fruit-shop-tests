package core.basesyntax.operation.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private PurchaseOperation operation;

    @BeforeEach
    void setUp() {
        operation = new PurchaseOperation();
    }

    @Test
    void handle_Ok() {
        FruitStorage.getStorage().put("carrot", 10);
        operation.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "carrot", 5));
    }

    @Test
    void handle_NotEnoughFruit_Ok() {
        FruitStorage.getStorage().put("carrot", 1);
        assertThrows(IllegalArgumentException.class,
                () -> operation.handle(
                        new FruitTransaction(
                                FruitTransaction.Operation.PURCHASE, "carrot", 10)));
    }

    @AfterAll
    static void cleanMeth() {
        FruitStorage.getStorage().clear();
    }
}
