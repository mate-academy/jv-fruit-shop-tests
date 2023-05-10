package core.basesyntax.dao.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

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
    public void Add_ok() {
        fruitTransactionDaoIml.add(appleTransaction);
        List<FruitTransaction> fruitTransactionList = fruitTransactionDaoIml.getAllListDb();
        assertEquals(1, fruitTransactionList.size());
        assertTrue(fruitTransactionList.contains(appleTransaction));
    }

    @Test
    public void Get_ok() {
        fruitTransactionDaoIml.add(appleTransaction);
        fruitTransactionDaoIml.add(bananaTransaction);
        FruitTransaction extend = fruitTransactionDaoIml.get("Apple");
        assertEquals(appleTransaction, extend);
        assertThrows(NoSuchElementException.class, () -> fruitTransactionDaoIml.get("Orange"));
    }

    @Test
    public void Update_Ok() {
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
    public void GetAllListDb_Ok() {
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
