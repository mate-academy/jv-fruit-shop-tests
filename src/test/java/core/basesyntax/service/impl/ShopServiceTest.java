package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.impl.BalanceOperation;
import core.basesyntax.operation.impl.OperationHandler;
import core.basesyntax.operation.impl.PurchaseOperation;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private static OperationStrategy operationStrategy;
    private static ShopService service;
    private static List<FruitTransaction> transactions;
    private static final FruitTransaction.Operation balance = FruitTransaction.Operation.BALANCE;

    @BeforeAll
    static void beforeAll() {
        operationHandlers = new HashMap<>() {{
               put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
               put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        }};
            operationStrategy = new OperationStrategyImpl(operationHandlers);
            service = new ShopServiceImpl(operationStrategy);
            transactions = Arrays.asList(new FruitTransaction(balance,new Fruit("banana"),20),
                new FruitTransaction(balance, new Fruit("banana"), 55));
    }

    @Test
    void process_transactionExist_ok() {
        Map.Entry<String, Integer> expected = Map.entry("banana", 75);
        assertTrue(service.process(transactions).entrySet().contains(expected));
    }

    @Test
    void process_negativeValue_notOk() {
        Fruit fruitNegativeQuantity = new Fruit("banana");
        transactions.set(1, new FruitTransaction(FruitTransaction.Operation.BALANCE,
                fruitNegativeQuantity, -55));
        assertThrows(RuntimeException.class, () -> service.process(transactions));
    }
}
