package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.stategy.OperationHandler;
import core.basesyntax.stategy.PurchaseOperationHandlerImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerImplTest {
    private static Integer expected;
    private static Fruit fruit;
    private static TransactionDto transactionDto;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        Storage.getDataBase().put(new Fruit("banana"), 15);
        operationHandler = new PurchaseOperationHandlerImpl();
        transactionDto = new TransactionDto("p", "banana", 10);
        fruit = new Fruit(transactionDto.getFruitName());
    }

    @Test
    public void correctWorking_purchaseOperationHandler_ok() {
        expected = 5;
        operationHandler.apply(transactionDto);
        Assert.assertEquals(expected, Storage.getDataBase()
                .get(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void oldQuantityLessThanQuantity_notOk() {
        TransactionDto transactionDto1 = new TransactionDto("p", "banana", 20);
        operationHandler.apply(transactionDto1);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getDataBase().clear();
    }
}
