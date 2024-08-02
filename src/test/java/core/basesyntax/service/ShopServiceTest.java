package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {
    private static ShopService shopService;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation()
        );
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
    }

    @Test
    void process_processValidTransactions_ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10)
        );
        Map<String, Integer> expected = Map.of(
                "apple", 110,
                "banana", 20
        );
        shopService.process(transactions);
        assertEquals(expected, Storage.getStorage());
    }

    @Test
    void process_processTransactionsWithNullOperation() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(null, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10)
        );
        assertThrows(RuntimeException.class,
                () -> shopService.process(transactions));
    }
}
