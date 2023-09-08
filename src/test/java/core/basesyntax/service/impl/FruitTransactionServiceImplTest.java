package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    private static FruitTransactionServiceImpl fruitTransactionService;

    @BeforeAll
    static void beforeAll() {
        OperationStrategy operationStrategy = new OperationStrategyImpl();
        fruitTransactionService = new FruitTransactionServiceImpl(operationStrategy);
    }

    @Test
    void processTransaction_Ok() {
        List<FruitTransaction> transactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50));
        fruitTransactionService.processTransactions(transactionList);
        Map<String, Integer> expected = Map.of("banana", 50);
        Map<String, Integer> actual = Storage.fruitTransactionsMap;
        assertEquals(expected, actual);
    }
}
