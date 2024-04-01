package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.ReturnStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ReturnHandlerTest {
    @Test
    public void handle_ValidTransaction_UpdatesReturn_Ok() {
        ReturnStrategy returnStrategy = new ReturnStrategy();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.RETURN, "banana", 100);
        Map<String, Integer> fruitStore = new HashMap<>();
        returnStrategy.handleTransaction(transaction, fruitStore);

        int expectedQuantity = 100;
        int actualQuantity = fruitStore.get("banana");

        assertEquals(expectedQuantity, actualQuantity);

    }
}
