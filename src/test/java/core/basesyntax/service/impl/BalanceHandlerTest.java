package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerTest {
    private BalanceHandler balanceHandler = new BalanceHandler();
    private final String expected = "fruit,quantity" + System.lineSeparator()
            + "banana," + 50 + System.lineSeparator()
            + "apple," + 35 + System.lineSeparator() + "mango," + 12 + System.lineSeparator();
    private final FruitRecordDto fruitRecordDto = new FruitRecordDto("b", "banana", 20);

    @Before
    public void setUp() {
        Storage.getFruits().clear();
    }

    @Test
    public void apply_CorrectFruitRecordDtoWithOperationBalance_Ok() {
        Fruit fruit = new Fruit("banana");
        Storage.fruits.put(fruit, 20);
        int expected = 20;
        int actual = balanceHandler.apply(fruitRecordDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeBalance_CorrectWriteBalance_Ok() {
        Map<Fruit, Integer> balance = new HashMap<>();
        balance.put(new Fruit("banana"), 50);
        balance.put(new Fruit("apple"), 35);
        balance.put(new Fruit("mango"), 12);
        String actual = balanceHandler.writeBalance(balance);
        Assert.assertEquals(expected, actual);
    }
}
