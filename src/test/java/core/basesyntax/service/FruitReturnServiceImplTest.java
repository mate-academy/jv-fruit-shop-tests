package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.interfaces.FruitOperationService;
import org.junit.Before;
import org.junit.Test;

public class FruitReturnServiceImplTest {
    private OperationType type;
    private FruitOperationService fruitReturnService;
    private Fruit fruit;

    @Before
    public void setUp() {
        type = OperationType.RETURN;
        fruitReturnService = new FruitReturnServiceImpl(
                new FruitDaoImpl());
        fruit = new Fruit("banana");
        Storage.getFruitDataBase().entrySet().clear();
    }

    @Test
    public void apply_putElements_Ok() {
        Storage.getFruitDataBase().put(fruit, 11);
        fruitReturnService.apply(new TransactionDto(type, fruit, 78));
        int expected = 89;
        int actual = Storage.getFruitDataBase().get(fruit);
        assertEquals(expected, actual);
    }

    @Test
    public void apply_putNullElement_Ok() {
        Storage.getFruitDataBase().put(null, 2);
        fruitReturnService.apply(new TransactionDto(type, null, 13));
        int expected = 15;
        int actual = Storage.getFruitDataBase().get(null);
        assertEquals(expected, actual);
    }
}
