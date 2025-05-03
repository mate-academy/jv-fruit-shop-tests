package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.SupplyTransactionService;
import core.basesyntax.strategy.TransactionService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionStrategyServiceTest {

    private static TransactionStrategyService strategyService;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, TransactionService> operations = new HashMap<>();
        operations.put(FruitTransaction.Operation.SUPPLY, new SupplyTransactionService());
        strategyService = new TransactionStrategyService(operations);
    }

    @Test
    void getTransactionService_Ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "test1", 1);
        TransactionService result = strategyService.getTransactionService(transaction);
        assertEquals(SupplyTransactionService.class, result.getClass());
    }

    @Test
    void getTransactionService_UnexpectedOperation_NotOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "test1", 1);
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> strategyService.getTransactionService(transaction));
        assertEquals(exception.getMessage(), "Unexpected transaction operation: "
                + transaction.getOperation());
    }
}
