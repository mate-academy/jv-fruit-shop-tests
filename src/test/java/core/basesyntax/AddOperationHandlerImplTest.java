package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.stategy.AddOperationHandlerImpl;
import core.basesyntax.stategy.OperationHandler;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerImplTest {
    private static Integer expected;
    private static Fruit fruit;
    private static TransactionDto transactionDto;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        Storage.getDataBase().put(new Fruit("banana"), 15);
        operationHandler = new AddOperationHandlerImpl();
        transactionDto = new TransactionDto("b", "banana", 10);
        fruit = new Fruit(transactionDto.getFruitName());
    }

    @Test
    public void correctWorking_addOperationHandler_ok() {
        expected = 25;
        operationHandler.apply(transactionDto);
        Assert.assertEquals(expected, Storage.getDataBase()
                .get(fruit));
    }

    @AfterClass
    public static void afterClass() {
        Storage.getDataBase().clear();
    }
}
