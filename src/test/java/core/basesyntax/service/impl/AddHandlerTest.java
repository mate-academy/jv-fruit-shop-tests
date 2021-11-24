package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddHandlerTest {
    private final AddHandler addHandler = new AddHandler(new FruitDaoImpl());
    private Map<Fruit,Integer> expectedMap;

    @Before
    public void setUp() {
        expectedMap = new HashMap<>();
        Storage.fruits.clear();
    }

    @Test
    public void add_NewData_Ok() {
        expectedMap.put(new Fruit("banana"),30);
        addHandler.apply(new Transaction("b","banana",20));
        addHandler.apply(new Transaction("s","banana",5));
        addHandler.apply(new Transaction("r","banana",5));
        Assert.assertEquals(expectedMap, Storage.fruits);
    }

    @Test
    public void add_OldData_Ok() {
        expectedMap.put(new Fruit("apple"),15);
        Storage.fruits.put(new Fruit("apple"),10);
        addHandler.apply(new Transaction("s","apple",5));
        Assert.assertEquals(expectedMap, Storage.fruits);
    }
}
