package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitDto;
import org.junit.After;
import org.junit.Test;

public class ReturnOperationTest {

    @Test
    public void changeCountFruitAfterReturnOperation_Ok() {
        FruitDto fruitDto = new FruitDto("p", "banana", 10);
        Storage.fruits.put(fruitDto.getFruit(), fruitDto.getQuantity());

        Storage.fruits.put(fruitDto.getFruit(), Storage.fruits.get(fruitDto.getFruit())
                + fruitDto.getQuantity());
    }

    @After
    public void after() {
        Storage.fruits.clear();
    }
}
