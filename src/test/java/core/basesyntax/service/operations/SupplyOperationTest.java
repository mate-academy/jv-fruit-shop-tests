package core.basesyntax.service.operations;

import static core.basesyntax.storage.Storage.shop;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static SupplyOperation supplyOperation;
    private static BalanceOperation balanceOperation;

    @BeforeAll
    public static void setUp() {
        supplyOperation = new SupplyOperation();
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
    public void supplyFruits_Ok() {
        FruitTransaction appleSupply =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 1);
        FruitTransaction bananaSupply =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10);
        FruitTransaction cherrySupply =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "cherry", 100);
        supplyOperation.processTransaction(appleSupply);
        supplyOperation.processTransaction(bananaSupply);
        supplyOperation.processTransaction(cherrySupply);
        assertEquals(2, shop.get("apple"));
        assertEquals(20, shop.get("banana"));
        assertEquals(200, shop.get("cherry"));
    }

    @Test
    public void supplyFruitWhichIsNotInBalance_Ok() {
        FruitTransaction appleSupply =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 1);
        FruitTransaction bananaSupply =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10);
        FruitTransaction cherrySupply =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "pear", 100);
        supplyOperation.processTransaction(appleSupply);
        supplyOperation.processTransaction(bananaSupply);
        supplyOperation.processTransaction(cherrySupply);
        assertEquals(2, shop.get("apple"));
        assertEquals(20, shop.get("banana"));
        assertEquals(200, shop.get("pear"));
    }

    @Test
    public void supplyFruitsWithNegativeQuantity_NotOk() {
        FruitTransaction appleSupply =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 1);
        FruitTransaction bananaSupply =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10);
        FruitTransaction cherrySupply =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "cherry", -100);
        supplyOperation.processTransaction(appleSupply);
        supplyOperation.processTransaction(bananaSupply);
        Exception exception = assertThrows(RuntimeException.class,
                () -> supplyOperation.processTransaction(cherrySupply));
        String expectedMessage = "The quantity is negative";
        String actualMessage = exception.getMessage();
        assertEquals(2, shop.get("apple"));
        assertEquals(20, shop.get("banana"));
        assertEquals(expectedMessage, actualMessage);
    }
}

