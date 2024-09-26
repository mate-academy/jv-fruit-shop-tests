package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.models.TransactionDto;
import core.basesyntax.services.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperationHandler = new PurchaseOperationHandler(new StorageDaoImpl());
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void apply_purchaseExist_Ok() {
        String fruitName = "apple";
        int currentValue = 100;
        Storage.storage.put(fruitName, currentValue);
        int valueToPurchase = 50;
        TransactionDto transactionDto
                = new TransactionDto("p", fruitName, valueToPurchase);
        purchaseOperationHandler.apply(transactionDto);
        int actual = Storage.storage.get(fruitName);
        assertEquals("Wrong result value.",
                currentValue - valueToPurchase, actual);
    }

    @Test
    public void apply_purchaseZero_Ok() {
        String fruitName = "apple";
        int currentValue = 100;
        Storage.storage.put(fruitName, currentValue);
        TransactionDto transactionDto
                = new TransactionDto("b", fruitName, 0);
        purchaseOperationHandler.apply(transactionDto);
        int actual = Storage.storage.get(fruitName);
        assertEquals("Wrong result value.",
                currentValue, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_firstTimePurchase_NotOk() {
        String fruitName = "apple";
        int valueToPurchase = 1;
        TransactionDto transactionDto
                = new TransactionDto("p", fruitName, valueToPurchase);
        purchaseOperationHandler.apply(transactionDto);
    }

    @Test(expected = RuntimeException.class)
    public void apply_purchaseZeroFirstTime_NotOk() {
        String fruitName = "apple";
        TransactionDto transactionDto
                = new TransactionDto("b", fruitName, 0);
        purchaseOperationHandler.apply(transactionDto);
    }

    @Test(expected = RuntimeException.class)
    public void apply_purchaseMoreThenExist_NotOk() {
        String fruitName = "apple";
        int currentValue = 100;
        Storage.storage.put(fruitName, currentValue);
        TransactionDto transactionDto
                = new TransactionDto("b", fruitName, 150);
        purchaseOperationHandler.apply(transactionDto);
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullValue_NotOk() {
        purchaseOperationHandler.apply(null);
    }

}
