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

class SupplyOperationHandlerTest {
    private static final String TEST_FRUIT_NAME = "testFruitName";
    private static final int TEST_QUANTITY = 77;
    private static final Map<String, Integer> expectedMap = new HashMap<>();
    private static OperationHandler operationHandler;
    private static FruitTransaction supplyTransaction;

    @BeforeAll
    static void initVariables() {
        operationHandler = new SupplyOperationHandler();

        supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(Operation.SUPPLY);
        supplyTransaction.setFruitName(TEST_FRUIT_NAME);
        supplyTransaction.setQuantity(TEST_QUANTITY);

        expectedMap.put(TEST_FRUIT_NAME, TEST_QUANTITY);
    }

    @AfterAll
    static void clearMap() {
        FruitDb.getBalanceMap().clear();
    }

    @Test
    void handleTransaction_supplyTransaction_ok() {
        operationHandler.handleTransaction(supplyTransaction);

        Map<String, Integer> actualMap = FruitDb.getBalanceMap();

        assertEquals(expectedMap, actualMap);
    }
}
