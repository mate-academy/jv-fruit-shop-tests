package core.basesyntax.fruitshop.service.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.fruitshop.fruitstoragedb.FruitStorage;
import core.basesyntax.fruitshop.model.Fruit;
import core.basesyntax.fruitshop.model.OperationType;
import core.basesyntax.fruitshop.model.RecordDto;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {

    private BalanceOperationHandler operationHandler;
    private Map<Fruit,Integer> expectrdStorage;
    private Map<Fruit,Integer> actualStorage;

    @Before
    public void setUp() throws Exception {
        operationHandler = new BalanceOperationHandler();
        expectrdStorage = new HashMap<>();
    }

    @Test(expected = RuntimeException.class)
    public void containsBalance_applyOperation_Ok() {
        RecordDto firstBalance = new RecordDto(OperationType.BALANCE, new Fruit("orange"), 40);
        RecordDto secondBalance = new RecordDto(OperationType.BALANCE, new Fruit("orange"), 10);
        operationHandler.applyOperation(firstBalance);
        operationHandler.applyOperation(secondBalance);
    }

    @Test
    public void notContainsBalance_applyOperation_Ok() {
        RecordDto balance = new RecordDto(OperationType.BALANCE, new Fruit("orange"), 10);
        expectrdStorage.put(new Fruit("orange"), 10);
        operationHandler.applyOperation(balance);
        actualStorage = FruitStorage.getStorage();
        assertEquals(expectrdStorage, actualStorage);
    }

    @After
    public void afterEachTest() throws Exception {
        FruitStorage.getStorage().clear();
    }
}
