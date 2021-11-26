package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddHandlerTest {
    private final AddHandler addHandler = new AddHandler(new FruitDaoImpl());

    @Before
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void add_dataToEmptyStorage_Ok() {
        addHandler.apply(new Transaction("b","banana",20));
        addHandler.apply(new Transaction("s","banana",5));
        addHandler.apply(new Transaction("r","banana",5));
        Assert.assertEquals(Integer.valueOf(30), Storage.fruits.get(new Fruit("banana")));
    }

    @Test
    public void add_dataToNotEmptyStorage_Ok() {
        Fruit fruit = new Fruit("apple");
        Storage.fruits.put(fruit,10);
        addHandler.apply(new Transaction("s","apple",5));
        Assert.assertEquals(Integer.valueOf(15), Storage.fruits.get(fruit));
    }
}
