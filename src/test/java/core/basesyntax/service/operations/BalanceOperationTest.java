package core.basesyntax.service.operations;

import static core.basesyntax.storage.Storage.shop;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static BalanceOperation balanceOperation;

    @BeforeAll
    public static void setUp() {
        balanceOperation = new BalanceOperation();
    }

    @Test
    public void addBalance_Ok() {
        FruitTransaction appleBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 1);
        FruitTransaction bananaBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        FruitTransaction cherryBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "cherry", 100);
        balanceOperation.processTransaction(appleBalance);
        balanceOperation.processTransaction(bananaBalance);
        balanceOperation.processTransaction(cherryBalance);
        assertEquals(1, shop.get("apple"));
        assertEquals(10, shop.get("banana"));
        assertEquals(100, shop.get("cherry"));
    }

    @Test
    public void addNegativeQuantityToBalance_NotOk() {
        FruitTransaction appleBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 1);
        FruitTransaction bananaBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -10);
        FruitTransaction cherryBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "cherry", 0);
        balanceOperation.processTransaction(appleBalance);
        balanceOperation.processTransaction(cherryBalance);
        Exception exception = assertThrows(RuntimeException.class,
                () -> balanceOperation.processTransaction(bananaBalance));
        String expectedMessage = "The quantity is negative";
        String actualMessage = exception.getMessage();
        assertEquals(1, shop.get("apple"));
        assertEquals(0, shop.get("cherry"));
        assertEquals(expectedMessage, actualMessage);
    }
}
