package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperation;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperation;
import core.basesyntax.operation.ReturnOperation;
import core.basesyntax.operation.SupplyOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private static final String KIWI = "kiwi";
    private static final int QUANTITY_5 = 5;
    private static final int QUANTITY_10 = 10;
    private static final int QUANTITY_15 = 15;
    private static final int QUANTITY_20 = 20;
    private static final int QUANTITY_30 = 30;
    private static final int QUANTITY_50 = 50;

    @BeforeEach
    void setUp() {
        Storage.getFruitStorage().clear();
    }

    @Test
    void balanceOperation_apply_correctlyAddsBalance() {
        OperationHandler balanceOperation = new BalanceOperation();
        balanceOperation.apply(new FruitTransaction(APPLE, QUANTITY_50));
        assertEquals(QUANTITY_50, Storage.getFruitStorage().get(APPLE));
    }

    @Test
    void purchaseOperation_apply_correctlySubtractsQuantity() {
        Storage.getFruitStorage().put(BANANA, QUANTITY_50);
        OperationHandler purchaseOperation = new PurchaseOperation();
        purchaseOperation.apply(new FruitTransaction(BANANA, QUANTITY_30));
        assertEquals(QUANTITY_20, Storage.getFruitStorage().get(BANANA));
    }

    @Test
    void purchaseOperation_apply_throwsExceptionWhenNotEnoughStock() {
        Storage.getFruitStorage().put(BANANA, QUANTITY_5);
        OperationHandler purchaseOperation = new PurchaseOperation();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                purchaseOperation.apply(new FruitTransaction(BANANA, QUANTITY_10)));
        assertEquals("We don't have 10banana's . we have only 5", exception.getMessage());
    }

    @Test
    void returnOperation_apply_correctlyAddsReturnedFruits() {
        Storage.getFruitStorage().put(ORANGE, QUANTITY_10);
        OperationHandler returnOperation = new ReturnOperation();
        returnOperation.apply(new FruitTransaction(ORANGE, QUANTITY_5));
        assertEquals(QUANTITY_15, Storage.getFruitStorage().get(ORANGE));
    }

    @Test
    void supplyOperation_apply_correctlyIncreasesStock() {
        OperationHandler supplyOperation = new SupplyOperation();
        supplyOperation.apply(new FruitTransaction(KIWI, QUANTITY_20));
        assertEquals(QUANTITY_20, Storage.getFruitStorage().get(KIWI));
    }
}
