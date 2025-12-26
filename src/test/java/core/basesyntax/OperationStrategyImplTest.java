package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;
import strategy.BalanceOperation;
import strategy.OperationHandler;
import strategy.OperationStrategy;
import strategy.OperationStrategyImpl;
import strategy.PurchaseOperation;
import strategy.SupplyOperation;

class OperationStrategyImplTest {

    @Test
    void get_BalanceOperation_Ok() {
        Map<FruitTransaction.Operation, OperationHandler> strategyMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation()
        );

        OperationStrategy strategy = new OperationStrategyImpl(strategyMap);

        OperationHandler handler = strategy.get(FruitTransaction.Operation.BALANCE);

        assertEquals(BalanceOperation.class, handler.getClass());
    }
}
