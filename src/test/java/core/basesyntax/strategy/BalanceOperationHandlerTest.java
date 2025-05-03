package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitShopDao;
import core.basesyntax.db.FruitShopDaoCsvImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final String FRUIT = "apple";
    private static final String SECOND_FRUIT = "banana";
    private static final int POSITIVE_QUANTITY = 13;
    private static final int ZERO_QUANTITY = 0;
    private static FruitShopDao fruitShopDao;
    private static OperationHandler operationHandler;
    private static FruitTransaction validTransaction;
    private static FruitTransaction secondValidTransaction;

    @BeforeAll
    static void beforeAll() {
        fruitShopDao = new FruitShopDaoCsvImpl();
        operationHandler = new BalanceOperationHandler(fruitShopDao);
        validTransaction = new FruitTransaction(
                Operation.BALANCE, FRUIT, POSITIVE_QUANTITY);
        secondValidTransaction = new FruitTransaction(
                Operation.BALANCE, SECOND_FRUIT, ZERO_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitQuantities.clear();
    }

    @Test
    void handleOperation_newFruit_ok() {
        operationHandler.handleOperation(validTransaction);
        Integer expected = POSITIVE_QUANTITY;
        Integer actual = Storage.fruitQuantities.get(FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    void handleOperation_updateFruit_ok() {
        Storage.fruitQuantities.put(SECOND_FRUIT, POSITIVE_QUANTITY);
        operationHandler.handleOperation(secondValidTransaction);

        Integer expected = ZERO_QUANTITY;
        Integer actual = Storage.fruitQuantities.get(SECOND_FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    void handleOperation_twoFruits_ok() {
        operationHandler.handleOperation(validTransaction);
        operationHandler.handleOperation(secondValidTransaction);
        Set<String> expected = new HashSet<>();
        expected.add(FRUIT);
        expected.add(SECOND_FRUIT);
        Set<String> actual = Storage.fruitQuantities.keySet();

        assertEquals(expected, actual);
    }
}
