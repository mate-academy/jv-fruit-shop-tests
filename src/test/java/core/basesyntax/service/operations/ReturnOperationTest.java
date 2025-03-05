package core.basesyntax.service.operations;

import static core.basesyntax.storage.Storage.shop;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private static ReturnOperation returnOperation;
    private static BalanceOperation balanceOperation;

    @BeforeAll
    public static void setUp() {
        returnOperation = new ReturnOperation();
        balanceOperation = new BalanceOperation();
    }

    @BeforeEach
    public void addFruitsToBalance() {
        FruitTransaction appleBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 1);
        FruitTransaction bananaBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        FruitTransaction cherryBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "cherry", 100);
        balanceOperation.processTransaction(appleBalance);
        balanceOperation.processTransaction(bananaBalance);
        balanceOperation.processTransaction(cherryBalance);
    }

    @AfterEach
    public void makeShopEmpty() {
        shop.clear();
    }

    @Test
    public void returnFruits_Ok() {
        FruitTransaction appleReturn =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 1);
        FruitTransaction bananaReturn =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10);
        FruitTransaction cherryReturn =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "cherry", 100);
        returnOperation.processTransaction(appleReturn);
        returnOperation.processTransaction(bananaReturn);
        returnOperation.processTransaction(cherryReturn);
        assertEquals(2, shop.get("apple"));
        assertEquals(20, shop.get("banana"));
        assertEquals(200, shop.get("cherry"));
    }

    @Test
    public void returnFruitWhichIsNotInBalance_Ok() {
        FruitTransaction appleReturn =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 1);
        FruitTransaction bananaReturn =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10);
        FruitTransaction cherryReturn =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "pear", 100);
        returnOperation.processTransaction(appleReturn);
        returnOperation.processTransaction(bananaReturn);
        returnOperation.processTransaction(cherryReturn);
        assertEquals(2, shop.get("apple"));
        assertEquals(20, shop.get("banana"));
        assertEquals(200, shop.get("pear"));
    }

    @Test
    public void returnFruitsWithNegativeQuantity_NotOk() {
        FruitTransaction appleReturn =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 1);
        FruitTransaction bananaReturn =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10);
        FruitTransaction cherryReturn =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "cherry", -100);
        returnOperation.processTransaction(appleReturn);
        returnOperation.processTransaction(bananaReturn);
        Exception exception = assertThrows(RuntimeException.class,
                () -> returnOperation.processTransaction(cherryReturn));
        String expectedMessage = "The quantity is negative";
        String actualMessage = exception.getMessage();
        assertEquals(2, shop.get("apple"));
        assertEquals(20, shop.get("banana"));
        assertEquals(expectedMessage, actualMessage);
    }
}
