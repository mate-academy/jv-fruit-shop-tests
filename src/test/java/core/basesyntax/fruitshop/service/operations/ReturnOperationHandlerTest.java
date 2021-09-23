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

public class ReturnOperationHandlerTest {
    private Map<Fruit,Integer> actualFruitStorage;
    private ReturnOperationHandler returnProcess;
    private Map<Fruit, Integer> expectedFruitStorage;

    @Before
    public void setUp() throws Exception {
        returnProcess = new ReturnOperationHandler();
        expectedFruitStorage = new HashMap<>();
        actualFruitStorage = FruitStorage.getStorage();
        BalanceOperationHandler balance = new BalanceOperationHandler();
        balance.applyOperation(new RecordDto(OperationType.BALANCE, new Fruit("banana"), 5));
    }

    @Test
    public void returnAvailableInStorageFruits_applyOperation_Ok() {
        returnProcess.applyOperation(new RecordDto(OperationType.RETURN, new Fruit("banana"), 10));
        expectedFruitStorage.put(new Fruit("banana"), 15);
        assertEquals(expectedFruitStorage, actualFruitStorage);
    }

    @Test
    public void returnUnavailableFruits_applyOperation_Ok() {
        returnProcess.applyOperation(new RecordDto(OperationType.RETURN, new Fruit("apple"), 1));
        expectedFruitStorage.put(new Fruit("banana"), 5);
        expectedFruitStorage.put(new Fruit("apple"), 1);
        assertEquals(expectedFruitStorage, actualFruitStorage);
    }

    @After
    public void afterEachTest() throws Exception {
        FruitStorage.getStorage().clear();
    }
}
