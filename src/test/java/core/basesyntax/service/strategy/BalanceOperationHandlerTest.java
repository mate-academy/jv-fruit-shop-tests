package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitDb;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final String TEST_FRUIT_NAME = "testFruitName";
    private static final int TEST_QUANTITY = 77;
    private static final Map<String, Integer> expectedBalanceMap = new HashMap<>();
    private static OperationHandler operationHandler;
    private static FruitTransaction balanceTransaction;

    @BeforeAll
    static void initVariables() {
        operationHandler = new BalanceOperationHandler();

        balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(Operation.BALANCE);
        balanceTransaction.setFruitName(TEST_FRUIT_NAME);
        balanceTransaction.setQuantity(TEST_QUANTITY);

        expectedBalanceMap.put(TEST_FRUIT_NAME, TEST_QUANTITY);
    }

    @AfterAll
    static void clearMap() {
        FruitDb.getBalanceMap().clear();
    }

    @Test
    void handleTransaction_balanceTransaction_ok() {
        operationHandler.handleTransaction(balanceTransaction);

        Map<String, Integer> actualBalanceMap = FruitDb.getBalanceMap();

        assertEquals(expectedBalanceMap, actualBalanceMap);
    }
}
