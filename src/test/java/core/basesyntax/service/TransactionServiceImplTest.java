package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.StrategyService;
import service.TransactionService;
import service.impl.StrategyServiceImpl;
import service.impl.TransactionServiceImpl;
import strategy.BalanceOperation;
import strategy.PurchaseOperation;
import strategy.ReturnOperation;
import strategy.SupplyOperation;

public class TransactionServiceImplTest {
    private static StrategyService strategyService;
    private final TransactionService transactionService =
            new TransactionServiceImpl(strategyService);
    private List<FruitTransaction> transactions;

    @BeforeAll
    static void beforeAll() {
        strategyService = new StrategyServiceImpl(
                Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                        FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                        FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                        FruitTransaction.Operation.RETURN, new ReturnOperation()));
    }

    @Test
    void processTransactions_Ok() {
        transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 2));
        transactionService.processTransactions(transactions);
        assertEquals(17, Storage.fruits.get("banana"));
    }

    @Test
    void processEmptyList_Ok() {
        Storage.fruits.clear();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> transactionService.processTransactions(Collections.emptyList()));
        assertEquals("Transaction list is empty", exception.getMessage());
    }
}
