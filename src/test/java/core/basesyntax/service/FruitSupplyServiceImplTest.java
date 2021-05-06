package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.interfaces.FruitOperationService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitSupplyServiceImplTest {
    private OperationType type;
    private FruitOperationService fruitSupplyService = new FruitSupplyServiceImpl(
            new FruitDaoImpl());
    private Fruit fruit;

    @Before
    public void setUp() {
        type = OperationType.SUPPLY;
        fruitSupplyService = new FruitSupplyServiceImpl(
                new FruitDaoImpl());
        fruit = new Fruit("strawberry");
        Storage.getFruitDataBase().entrySet().clear();
    }

    @Test
    public void apply_putElements_Ok() {
        Storage.getFruitDataBase().put(fruit, 6);
        fruitSupplyService.apply(new TransactionDto(type, fruit, 44));
        int expected = 50;
        int actual = Storage.getFruitDataBase().get(fruit);
        assertEquals(expected, actual);
    }

    @Test
    public void apply_putNullElement_Ok() {
        Storage.getFruitDataBase().put(null, 13);
        fruitSupplyService.apply(new TransactionDto(type, null, 13));
        int expected = 26;
        int actual = Storage.getFruitDataBase().get(null);
        assertEquals(expected, actual);
    }
}
