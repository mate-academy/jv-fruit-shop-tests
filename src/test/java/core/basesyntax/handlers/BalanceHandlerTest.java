package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class BalanceHandlerTest {
    @Test
    public void handle_ValidTransaction_ShouldUpdateBalance_Ok() {
        BalanceStrategy balanceStrategy = new BalanceStrategy();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "banana", 100);
        Map<String, Integer> fruitStore = new HashMap<>();
        int expectedQuantity = 100;

        balanceStrategy.handleTransaction(transaction, fruitStore);

        int actualQuantity = fruitStore.get("banana");
        assertEquals(expectedQuantity, actualQuantity);
    }
}
