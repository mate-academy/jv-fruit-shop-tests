package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationService;
import core.basesyntax.service.impl.strategy.BalanceOperationServiceImpl;
import core.basesyntax.service.impl.strategy.PurchaseOperationServiceImpl;
import core.basesyntax.service.impl.strategy.ReturnOperationServiceImpl;
import core.basesyntax.service.impl.strategy.SupplyOperationServiceImpl;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitOperationStrategyTest {
    private static Map<FruitTransaction, OperationService> operationServices;
    private static FruitOperationStrategy operationStrategy;
    private static Set<FruitTransaction> transactions;

    @BeforeEach
    void setUp() {
        operationServices = Map.of(
                new FruitTransaction("b", "banana", 10), new BalanceOperationServiceImpl(),
                new FruitTransaction("r", "banana", 10), new ReturnOperationServiceImpl(),
                new FruitTransaction("p", "banana", 10), new PurchaseOperationServiceImpl(),
                new FruitTransaction("s", "banana", 10), new SupplyOperationServiceImpl());
        transactions = operationServices.keySet();
        operationStrategy = new FruitOperationStrategy();
    }

    @Test
    void getOperationService_fruitTransaction_Ok() {
        for (FruitTransaction operation : transactions) {
            Assertions.assertNotNull(operationStrategy.getOperationService(operation),
                    "Operation not found: " + operation);
        }
    }

    @Test
    void getOperationService_fruitTransactionIsNull_NotOk() {
        assertThrows(RuntimeException.class,
                () -> operationStrategy.getOperationService(null));
    }
}
