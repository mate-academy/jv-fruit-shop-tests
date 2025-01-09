package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.impl.BalanceOperation;
import core.basesyntax.operation.impl.OperationHandler;
import core.basesyntax.operation.impl.PurchaseOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ShopServiceTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers
            = new HashMap<>() {{
                    put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
                    put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
                    }};
    private OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
    private ShopService service = new ShopServiceImpl(operationStrategy);
    private List<FruitTransaction> transactions
            = Arrays.asList(new FruitTransaction(FruitTransaction
            .Operation.BALANCE,new Fruit("banana"),20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("banana"), 55));

    @Test
    void process_Ok() {
        assertTrue(service.process(transactions).entrySet().contains(Map.entry("banana", 75)));
    }

    @Test
    void process_negativeValue_NotOk() {
        transactions.set(1, new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("banana"), -55));
        assertThrows(RuntimeException.class, () -> service.process(transactions));
    }
}
