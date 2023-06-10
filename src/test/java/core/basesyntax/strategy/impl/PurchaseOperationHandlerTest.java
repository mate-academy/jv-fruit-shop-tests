package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.NegativeValueException;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.FruitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private StorageDao storage;
    private Transaction transaction;
    private FruitService fruitService;

    @BeforeEach
    public void setUp() {
        storage = new StorageDaoImpl();
        fruitService = new FruitServiceImpl(storage);
    }

    @Test
    public void purchaseOperation_OperateTransaction_ok() {
        transaction = new Transaction(Transaction.Operation.SUPPLY, "banana", 30);
        fruitService.add(transaction);
        transaction = new Transaction(Transaction.Operation.PURCHASE, "banana", 25);
        fruitService.remove(transaction);
        int expectedQuantity = 5;
        int actualQuantity = storage.getCurrentAmount("banana");
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void purchaseOperation_NegativeQuantity_notOk() {
        transaction = new Transaction(Transaction.Operation.PURCHASE, "apple", -15);
        assertThrows(NegativeValueException.class, () -> fruitService.remove(transaction));
    }

    @Test
    public void purchaseOperation_NegativeValueInRest_notOk() {
        transaction = new Transaction(Transaction.Operation.SUPPLY, "malen", 30);
        fruitService.add(transaction);
        transaction = new Transaction(Transaction.Operation.PURCHASE, "malen", 50);
        assertThrows(NegativeValueException.class, () -> fruitService.remove(transaction));
    }
}
