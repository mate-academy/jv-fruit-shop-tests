package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {

    @Test
    void handle_returnItems_correctInventory_OK() {
        ReturnOperation returnOperation = new ReturnOperation();
        Map<String, Integer> inventory = new HashMap<>(Map.of("apple", 50, "banana", 30));
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10)
        );
        returnOperation.handle(transactions, inventory);
        Assertions.assertEquals(70, inventory.get("apple"));
        Assertions.assertEquals(40, inventory.get("banana"));
    }

}
