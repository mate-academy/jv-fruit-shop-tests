package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationsTest {
    @BeforeEach
    void clear() {
        Storage.clear();
    }

    @Test
    void balanceOperation_setsValue() {
        new BalanceOperation().apply(new FruitTransaction(
                Operation.BALANCE, "banana", 30));
        assertEquals(30, Storage.getQuantity("banana"));
    }

    @Test
    void supplyOperation_addsValue() {
        Storage.setQuantity("apple", 5);
        new SupplyOperation().apply(new FruitTransaction(
                Operation.SUPPLY, "apple", 10));
        assertEquals(15, Storage.getQuantity("apple"));
    }

    @Test
    void purchaseOperation_ok() {
        Storage.setQuantity("orange", 10);
        new PurchaseOperation().apply(new FruitTransaction(
                Operation.PURCHASE, "orange", 4));
        assertEquals(6, Storage.getQuantity("orange"));
    }

    @Test
    void purchaseOperation_notEnough_throws() {
        Storage.setQuantity("orange", 2);
        assertThrows(RuntimeException.class, () ->
                new PurchaseOperation().apply(new FruitTransaction(
                        Operation.PURCHASE, "orange", 5)));
    }

    @Test
    void returnOperation_addsValue() {
        Storage.setQuantity("pear", 7);
        new ReturnOperation().apply(new FruitTransaction(
                Operation.RETURN, "pear", 3));
        assertEquals(10, Storage.getQuantity("pear"));
    }
}
