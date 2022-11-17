package core.basesyntax.service.operations;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;


import static org.junit.Assert.*;

public class PurchaseTest {
    private static Map<String, Integer> testStorage;
    private static Balance balance;

    @Test
    public void checkGetBalanceOperation_Ok() {
        testStorage = new HashMap<>();
        balance = new Balance();
        balance.makeOperation("apple", 123);
        assertEquals(123, (int) testStorage.get("banana"));
        testStorage.clear();
    }
}
