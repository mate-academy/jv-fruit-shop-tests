package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.IllegalQuantityException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final BalanceHandler balanceHandler = new BalanceHandler();
    private static final PurchaseHandler purchaseHandler = new PurchaseHandler();
    private static final Map<String, Integer> RESULT_MAP = Map.of("apple", 5);

    @BeforeEach
    void setUp() {
        FruitTransaction fruitTransactionBalance
                = new FruitTransaction(Operation.BALANCE, "apple", 10);

        Storage.fruitStorage.clear();
        balanceHandler.handleOperation(fruitTransactionBalance);
    }

    @Test
    void handleOperation_normalValue_Ok() {
        FruitTransaction fruitTransactionPurchase
                = new FruitTransaction(Operation.PURCHASE, "apple", 5);

        purchaseHandler.handleOperation(fruitTransactionPurchase);

        Assertions.assertEquals(Storage.fruitStorage, RESULT_MAP);
    }

    @Test
    void handleOperation_negativeValue_Ok() {
        FruitTransaction fruitTransactionPurchase
                = new FruitTransaction(Operation.PURCHASE, "apple", -10);

        Assertions.assertThrows(IllegalQuantityException.class,
                () -> purchaseHandler.handleOperation(fruitTransactionPurchase));
    }

    @Test
    void handleOperation_leadToNegativeValue_Ok() {
        FruitTransaction fruitTransactionPurchase
                = new FruitTransaction(Operation.PURCHASE, "apple", 20);

        Assertions.assertThrows(IllegalQuantityException.class,
                () -> purchaseHandler.handleOperation(fruitTransactionPurchase));
    }
}
