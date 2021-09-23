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

public class SupplyOperationHandlerTest {
    private Map<Fruit,Integer> actualFruitStorage;
    private SupplyOperationHandler supplyProcess;
    private Map<Fruit, Integer> expectedFruitStorage;

    @Before
    public void setUp() throws Exception {
        expectedFruitStorage = new HashMap<>();
        supplyProcess = new SupplyOperationHandler();
        actualFruitStorage = FruitStorage.getStorage();
    }

    @Test
    public void supplyUnavailableFruits_applyOperation_Ok() {
        supplyProcess
                .applyOperation(new RecordDto(OperationType.SUPPLY, new Fruit("pineapple"), 0));
        expectedFruitStorage.put(new Fruit("pineapple"), 0);
        assertEquals(actualFruitStorage, expectedFruitStorage);
    }

    @After
    public void afterEachTest() throws Exception {
        FruitStorage.getStorage().clear();
    }
}
