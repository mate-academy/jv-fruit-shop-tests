package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static BalanceOperation balanceOperation;

    @BeforeAll
    public static void setUp() {
        balanceOperation = new BalanceOperation();
    }

    @BeforeEach
    public void resetTheStateOfShopBetweenTest_Ok() {
        Storage.shop.clear();
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
        assertEquals(1, Storage.shop.get("apple"));
        assertEquals(10, Storage.shop.get("banana"));
        assertEquals(100, Storage.shop.get("cherry"));
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
        assertEquals(1, Storage.shop.get("apple"));
        assertEquals(0, Storage.shop.get("cherry"));
        assertEquals(expectedMessage, actualMessage);
    }
}
