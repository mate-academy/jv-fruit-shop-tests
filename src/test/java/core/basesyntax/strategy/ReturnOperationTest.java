package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {

    @Test
    void handle_returnItems_correctInventory_OK() {
        ReturnOperation returnOperation = new ReturnOperation();
        Map<String, Integer> inventory = new HashMap<>(Map.of("apple", 50, "banana", 30));
        FruitTransaction transaction1 =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 20);
        FruitTransaction transaction2 =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10);
        returnOperation.handle(transaction1, inventory);
        returnOperation.handle(transaction2, inventory);
        assertEquals(70, inventory.get("apple"));
        assertEquals(40, inventory.get("banana"));
    }

}
