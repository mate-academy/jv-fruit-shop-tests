package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitDto;
import org.junit.After;
import org.junit.Test;

public class SupplyOperationTest {
    private final TypeHandler supplyOperation = new SupplyOperation();

    @Test
    public void changeCountFruitAfterSupplyOperation_Ok() {
        FruitDto fruitDto = new FruitDto("p", "banana", 10);
        Storage.fruits.put(fruitDto.getFruit(), 0);
        Integer expected = 10;
        supplyOperation.handle(fruitDto);
        Integer actual = Storage.fruits.get(fruitDto.getFruit());
        assertEquals(expected, actual);
    }

    @After
    public void after() {
        Storage.fruits.clear();
    }
}

