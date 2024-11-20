package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {

    @Test
    void execute_correctHandlerExecuted_OK() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation()
        );
        OperationStrategy strategy = new OperationStrategy(handlers);
        Map<String, Integer> inventory = new HashMap<>(Map.of("apple", 50, "banana", 30));
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"apple", 20);
        strategy.execute(FruitTransaction.Operation.SUPPLY, transaction, inventory);
        assertEquals(70, inventory.get("apple"));
    }

}
