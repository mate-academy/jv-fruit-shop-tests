package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionExecutorTest {
    private Map<FruitTransaction.Operation, OperationHandler> strategyMap = new HashMap<>();

    @BeforeEach
    void setUp() {
        strategyMap.clear();
    }

    @Test
    void execute_emptyList_InvalidDataException() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        TransactionExecutor transactionExecutor = new TransactionExecutor(strategyMap);
        assertThrows(InvalidDataException.class, () ->
                transactionExecutor.execute(fruitTransactions), "Invalid data");
    }
}
