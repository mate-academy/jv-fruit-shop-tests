package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StrategyOperationImplTest {
    private List<FruitRecordDto> data;
    private Map<String, FruitOperationHandler> operationMap;

    @Before
    public void setUp() {
        Storage.fruits.clear();
        operationMap = new HashMap<>();
        operationMap.put("b", new BalanceHandler());
        operationMap.put("s", new AddOperationHandler());
        operationMap.put("p", new RemoveOperationHandler());
        operationMap.put("r", new AddOperationHandler());
        data = new ArrayList<>();
    }

    @Test
    public void get_operationBalance_Ok() {
        data.add(new FruitRecordDto("b", "banana", 40));
        data.add(new FruitRecordDto("b", "apple", 40));
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 40);
        expected.put(new Fruit("apple"), 40);
        Assert.assertEquals(expected, new StrategyOperationImpl(operationMap).get(data));
    }
}
