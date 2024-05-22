package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private final FruitTransaction transaction =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
    private final Map<String, Integer> expectedBalanceStatistic = Map.of("banana", 21);
    private OperationHandler operation;

    @BeforeEach
    void setUp() {
        operation = new ReturnOperationHandler();
        Storage.balanceStatistic.put("banana", 11);
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
