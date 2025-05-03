package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.operation.BalanceHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static TransactionDto transactionDto;

    @BeforeClass
    public static void setUp() {
        transactionDto = new TransactionDto(OperationType.BALANCE, new Fruit("apple"), 32);
    }

    @Test
    public void getOperationResult_CheckValidDataForBalanceHandler_Ok() {
        Integer actual = new BalanceHandler().getOperationResult(transactionDto);
        Integer expected = 32;
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        FruitsStorage.fruitsStorage.clear();
    }
}
