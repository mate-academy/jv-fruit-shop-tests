package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<String, Transaction> transactionMap = Map.of(
                "b", new BalanceTransaction()
        );
        operationStrategy = new OperationStrategyImpl(transactionMap);
    }

    @AfterEach
    void cleanUp() {
        Storage.getFruits().clear();
    }

    @Test
    void getOperationType_balanceInput_ok() {
        String input = "b";
        assertInstanceOf(BalanceTransaction.class,operationStrategy.getOperationType(input));
    }

    @Test
    void getOperationType_incorrectInput_notOk() {
        String input = "x";
        assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getOperationType(input));
    }
}
