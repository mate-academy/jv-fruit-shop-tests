package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.operation.SupplyHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static TransactionDto transactionDto;

    @BeforeClass
    public static void setUp() {
        transactionDto = new TransactionDto(
                OperationType.SUPPLY,
                new Fruit("apple"), 20);
    }

    @Test
    public void getOperationResult_CheckSupplyHandlerValidData_Ok() {
        FruitsStorage.fruitsStorage.put(new Fruit("apple"), 10);
        Integer expected = 30;
        Integer actual = new SupplyHandler().getOperationResult(
                        transactionDto);
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        FruitsStorage.fruitsStorage.clear();
    }
}
