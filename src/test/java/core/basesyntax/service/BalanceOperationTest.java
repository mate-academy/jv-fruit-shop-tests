package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitDto;
import org.junit.After;
import org.junit.Test;

public class BalanceOperationTest {
    private final TypeHandler balanceOperation = new BalanceOperation();

    @Test
    public void addFruitToStorage_Ok() {
        FruitDto fruitDto = new FruitDto("b", "banana", 100);
        Integer expected = 100;
        balanceOperation.handle(fruitDto);
        Integer actual = Storage.fruits.get(fruitDto.getFruit());
        assertEquals(expected, actual);
    }

    @After
    public void after() {
        Storage.fruits.clear();
    }
}
