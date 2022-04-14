package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.model.dao.FruitDaoImpl;
import core.basesyntax.model.dto.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.interfaces.OperationHandler;
import core.basesyntax.storage.FruitStorage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AddOperationHandlerTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private final OperationHandler operationHandler = new AddOperationHandler(new FruitDaoImpl());

    @Test
    public void apply_addNew_Ok() {
        FruitRecordDto recordDto = new FruitRecordDto(Operation.BALANCE, "orange", 25);
        operationHandler.apply(recordDto);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("orange"), 25);
        Map<Fruit, Integer> actual = FruitStorage.getFruits();
        assertEquals(expected, actual);
    }

    @Test
    public void apply_addMaxValue_notOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Quantity can't be larger than");
        FruitStorage.getFruits().put(new Fruit("orange"), 100_000);
        FruitRecordDto recordDto =
                new FruitRecordDto(Operation.SUPPLY, "orange", Integer.MAX_VALUE);
        operationHandler.apply(recordDto);
    }

    @After
    public void clearStorage() {
        FruitStorage.getFruits().clear();
    }
}
