package core.basesyntax.provider;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseStrategy;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class StrategyProviderTest {
    private StrategyProvider strategyProvider;

    @Test
    public void get_getCorrectStrategy_ok() {
        Map<Operation, OperationStrategy> strategies = new HashMap<>();
        strategies.put(Operation.RETURN, new ReturnOperation());
        strategies.put(Operation.PURCHASE, new PurchaseStrategy());
        strategies.put(Operation.BALANCE, new BalanceOperation());
        strategies.put(Operation.SUPPLY, new SupplyStrategy());
        this.strategyProvider = new StrategyProvider(strategies);

        OperationStrategy operationStrategy = this.strategyProvider.get(Operation.BALANCE);

        assertNotNull(operationStrategy);
    }

    @Test
    public void get_getNullIfStrategyIsNotExist_notOk() {
        Map<Operation, OperationStrategy> strategies = new HashMap<>();
        strategies.put(Operation.PURCHASE, new PurchaseStrategy());
        strategies.put(Operation.BALANCE, new BalanceOperation());
        strategies.put(Operation.SUPPLY, new SupplyStrategy());
        this.strategyProvider = new StrategyProvider(strategies);

        OperationStrategy operationStrategy = this.strategyProvider.get(Operation.RETURN);

        assertNull(operationStrategy);
    }
}
