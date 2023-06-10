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

class BalanceOperationHandlerTest {
    private StorageDao storage;
    private Transaction transaction;
    private FruitService fruitService;

    @BeforeEach
    public void setUp() {
        storage = new StorageDaoImpl();
        fruitService = new FruitServiceImpl(storage);
    }

    @Test
    public void balanceOperation_OperateTransaction_ok() {
        transaction = new Transaction(Transaction.Operation.BALANCE, "banana", 25);
        fruitService.add(transaction);
        int expectedQuantity = 25;
        int actualQuantity = storage.getCurrentAmount("banana");
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void balanceOperation_NegativeQuantity_notOk() {
        transaction = new Transaction(Transaction.Operation.BALANCE, "apple", -15);
        assertThrows(NegativeValueException.class, () -> fruitService.add(transaction));
    }
}
