package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static BalanceOperation balanceOperation;
    private Map<String, Integer> inventory;

    @BeforeAll
    public static void beforeAll() {
        balanceOperation = new BalanceOperation();
    }

    @BeforeEach
    public void setUp() {
        inventory = new HashMap<>();
    }

    @Test
    public void handle_balanceOperation_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);
        balanceOperation.handle(transaction, inventory);
        assertEquals(100, inventory.get("apple"));
    }
}
