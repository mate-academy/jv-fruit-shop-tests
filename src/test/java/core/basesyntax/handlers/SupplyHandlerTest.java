package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.SupplyStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class SupplyHandlerTest {
    @Test
    public void handle_ValidTransaction_ShouldUpdateSupply_Ok() {
        SupplyStrategy supplyStrategy = new SupplyStrategy();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, "banana", 100);
        Map<String, Integer> fruitStore = new HashMap<>();
        supplyStrategy.handleTransaction(transaction, fruitStore);

        int expectedQuantity = 100;
        int actualQuantity = fruitStore.get("banana");

        assertEquals(expectedQuantity, actualQuantity);

    }
}
