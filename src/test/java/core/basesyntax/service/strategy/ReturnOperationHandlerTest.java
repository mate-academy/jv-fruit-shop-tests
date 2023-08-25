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

class ReturnOperationHandlerTest {
    private static final String TEST_FRUIT_NAME = "testFruitName";
    private static final int TEST_QUANTITY = 77;
    private static final Map<String, Integer> expectedMap = new HashMap<>();
    private static final int EXPECTED_QUANTITY = 154;
    private static OperationHandler operationHandler;
    private static FruitTransaction returnTransaction;

    @BeforeAll
    static void initVariables() {
        operationHandler = new ReturnOperationHandler();

        returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(Operation.RETURN);
        returnTransaction.setFruitName(TEST_FRUIT_NAME);
        returnTransaction.setQuantity(TEST_QUANTITY);

        expectedMap.put(TEST_FRUIT_NAME, EXPECTED_QUANTITY);
    }

    @AfterAll
    static void clearMap() {
        FruitDb.getBalanceMap().clear();
    }

    @Test
    void handleTransaction_supplyTransaction_ok() {
        FruitDb.getBalanceMap().put(TEST_FRUIT_NAME, TEST_QUANTITY);
        operationHandler.handleTransaction(returnTransaction);

        Map<String, Integer> actualMap = FruitDb.getBalanceMap();

        assertEquals(expectedMap, actualMap);
    }
}
