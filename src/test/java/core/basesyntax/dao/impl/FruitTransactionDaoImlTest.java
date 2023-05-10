package core.basesyntax.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionDaoImlTest {
    private FruitTransactionDaoIml fruitTransactionDaoIml;
    private FruitTransaction appleTransaction;
    private FruitTransaction bananaTransaction;

    @BeforeEach
    public void setUp() {
        fruitTransactionDaoIml = new FruitTransactionDaoIml();
        appleTransaction = new FruitTransaction();
        appleTransaction.setFruit("Apple");
        appleTransaction.setQuantity(25);
        appleTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        bananaTransaction = new FruitTransaction();
        bananaTransaction.setFruit("Banana");
        bananaTransaction.setQuantity(20);
        bananaTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        Storage.fruits.clear();
    }

    @Test
    public void add_ok() {
        fruitTransactionDaoIml.add(appleTransaction);
        List<FruitTransaction> fruitTransactionList = fruitTransactionDaoIml.getAllListDb();
        assertEquals(1, fruitTransactionList.size());
        assertTrue(fruitTransactionList.contains(appleTransaction));
    }

    @Test
    public void get_ok() {
        fruitTransactionDaoIml.add(appleTransaction);
        fruitTransactionDaoIml.add(bananaTransaction);
        FruitTransaction extend = fruitTransactionDaoIml.get("Apple");
        assertEquals(appleTransaction, extend);
        assertThrows(NoSuchElementException.class, () -> fruitTransactionDaoIml.get("Orange"));
    }

    @Test
    public void update_Ok() {
        fruitTransactionDaoIml.add(appleTransaction);
        FruitTransaction extendTransaction = new FruitTransaction();
        extendTransaction.setFruit("Apple");
        extendTransaction.setQuantity(50);
        extendTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransactionDaoIml.update(extendTransaction);
        int actualSize = 1;
        assertEquals(actualSize, fruitTransactionDaoIml.getAllListDb().size());
        assertTrue(fruitTransactionDaoIml.getAllListDb().contains(extendTransaction));
        assertFalse(fruitTransactionDaoIml.getAllListDb().contains(appleTransaction));
    }

    @Test
    public void getAllListDb_Ok() {
        assertTrue(fruitTransactionDaoIml.getAllListDb().isEmpty());
        fruitTransactionDaoIml.add(appleTransaction);
        fruitTransactionDaoIml.add(bananaTransaction);
        List<FruitTransaction> result = fruitTransactionDaoIml.getAllListDb();
        int actualSize = 2;
        assertEquals(actualSize, result.size());
        assertTrue(result.contains(appleTransaction));
        assertTrue(result.contains(bananaTransaction));
    }
}
