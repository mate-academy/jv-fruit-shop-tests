package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static ShopService shopService;

    @BeforeEach
    void setUp() {
        OperationHandler balanceHandler = new BalanceOperation();
        OperationHandler supplyHandler = new SupplyOperation();
        OperationHandler purchaseHandler = new PurchaseOperation();
        OperationHandler returnHandler = new ReturnOperation();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        operationHandlers.put(FruitTransaction.Operation.RETURN, returnHandler);

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void test_process_nullTransactions_throwsException() {
        assertThrows(RuntimeException.class, () -> shopService.process(null));
    }

    @Test
    void test_process_emptyTransactions_throwsException() {
        assertThrows(RuntimeException.class, () -> shopService.process(new ArrayList<>()));
    }

    @Test
    void test_process_nullTransactionInList_throwsException() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
                null,
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20)
        );

        assertThrows(RuntimeException.class, () -> shopService.process(transactions));
    }
}
