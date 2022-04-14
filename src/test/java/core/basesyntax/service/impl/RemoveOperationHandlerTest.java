package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.IllegalOperationException;
import core.basesyntax.model.Operation;
import core.basesyntax.model.dao.FruitDao;
import core.basesyntax.model.dao.FruitDaoImpl;
import core.basesyntax.model.dto.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.interfaces.OperationHandler;
import core.basesyntax.storage.FruitStorage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RemoveOperationHandlerTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final OperationHandler removeOperationHandler = new RemoveOperationHandler(fruitDao);

    @Before
    public void setUp() {
        FruitStorage.getFruits().put(new Fruit("orange"), 75);
    }

    @Test
    public void apply_validRecord_Ok() {
        FruitRecordDto fruitRecord = new FruitRecordDto(Operation.RETURN, "orange", 25);
        removeOperationHandler.apply(fruitRecord);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("orange"), 50);
        Map<Fruit, Integer> actual = FruitStorage.getFruits();
        assertEquals(expected, actual);
    }

    @Test
    public void apply_invalidQuantity_notOk() {
        expectedException.expect(IllegalOperationException.class);
        expectedException.expectMessage("Can't purchase, not enough");
        FruitRecordDto fruitRecord = new FruitRecordDto(Operation.RETURN, "orange", 85);
        removeOperationHandler.apply(fruitRecord);
    }

    @After
    public void clearStorage() {
        FruitStorage.getFruits().clear();
    }
}
