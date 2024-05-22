package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.Constants;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static final String BANANA = Constants.BANANA;
    private final FruitTransaction transaction =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 10);
    private final Map<String, Integer> expectedBalanceStatistic = Map.of(BANANA, 21);
    private OperationHandler operation;

    @BeforeEach
    void setUp() {
        operation = new SupplyOperationHandler();
        Storage.balanceStatistic.put(BANANA, 11);
    }

    @Test
    void execute_Ok() {
        operation.execute(transaction);
        Map<String, Integer> actualBalanceStatistic = Storage.balanceStatistic;
        assertEquals(expectedBalanceStatistic, actualBalanceStatistic);
    }

    @AfterEach
    void tearDown() {
        Storage.balanceStatistic.clear();
    }
}
