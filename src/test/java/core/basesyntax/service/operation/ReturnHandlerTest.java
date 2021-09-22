package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.operation.ReturnHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static TransactionDto transactionDto;

    @BeforeClass
    public static void setUp() {
        transactionDto = new TransactionDto(
                OperationType.RETURN,
                new Fruit("apple"), 45);
    }

    @Test
    public void getOperationResult_CheckReturnHandlerValidData_Ok() {
        FruitsStorage.fruitsStorage.put(new Fruit("apple"), 70);
        Integer expected = 115;
        Integer actual = new ReturnHandler().getOperationResult(
                transactionDto);
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        FruitsStorage.fruitsStorage.clear();
    }

}
