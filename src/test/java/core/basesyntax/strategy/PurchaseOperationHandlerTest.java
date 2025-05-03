package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitShopDao;
import core.basesyntax.db.FruitShopDaoCsvImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final String FRUIT = "apple";
    private static final int POSITIVE_QUANTITY = 13;
    private static final int THREE_TIMES_POSITIVE_QUANTITY = 3 * POSITIVE_QUANTITY;
    private static final int ZERO_QUANTITY = 0;
    private static FruitShopDao fruitShopDao;
    private static OperationHandler operationHandler;
    private static FruitTransaction validTransaction;
    private static FruitTransaction secondValidTransaction;

    @BeforeAll
    static void beforeAll() {
        fruitShopDao = new FruitShopDaoCsvImpl();
        operationHandler = new PurchaseOperationHandler(fruitShopDao);
        validTransaction = new FruitTransaction(
                Operation.PURCHASE, FRUIT, POSITIVE_QUANTITY);
        secondValidTransaction = new FruitTransaction(
                Operation.PURCHASE, FRUIT, POSITIVE_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitQuantities.clear();
    }

    @Test
    void handleOperation_negativeNewQuantity_notOk() {
        Storage.fruitQuantities.put(FRUIT, ZERO_QUANTITY);
        assertThrows(RuntimeException.class,
                () -> operationHandler.handleOperation(validTransaction));
    }

    @Test
    void handleOperation_validQuantity_ok() {
        Storage.fruitQuantities.put(FRUIT, POSITIVE_QUANTITY);
        operationHandler.handleOperation(validTransaction);
        Integer expected = ZERO_QUANTITY;
        Integer actual = Storage.fruitQuantities.get(FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    void handleOperation_twoValidTransactions_ok() {
        Storage.fruitQuantities.put(FRUIT, THREE_TIMES_POSITIVE_QUANTITY);
        operationHandler.handleOperation(validTransaction);
        operationHandler.handleOperation(secondValidTransaction);
        Integer expected = POSITIVE_QUANTITY;
        Integer actual = Storage.fruitQuantities.get(FRUIT);
        assertEquals(expected, actual);
    }
}
