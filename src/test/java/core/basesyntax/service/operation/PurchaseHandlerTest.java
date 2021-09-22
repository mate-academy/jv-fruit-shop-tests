package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.operation.PurchaseHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static TransactionDto transactionDto;

    @BeforeClass
    public static void setUp() {
        transactionDto = new TransactionDto(OperationType.PURCHASE, new Fruit("banana"), 2);
    }

    @Test
    public void getOperationResult_CheckPurchaseHandlerValidData_Ok() {
        FruitsStorage.fruitsStorage.put(new Fruit("banana"), 70);
        int expected = new PurchaseHandler().getOperationResult(transactionDto);
        int actual = 68;
        assertEquals(expected,actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullFruit_NotOk() {
        new PurchaseHandler().getOperationResult(null);
    }

    @Test(expected = RuntimeException.class)
    public void getOperationResult_NotEnoughFruits_NotOk() {
        FruitsStorage.fruitsStorage.put(new Fruit("apple"), 70);
        new PurchaseHandler().getOperationResult(transactionDto);
    }

    @After
    public void afterEachTest() {
        FruitsStorage.fruitsStorage.clear();
    }
}
