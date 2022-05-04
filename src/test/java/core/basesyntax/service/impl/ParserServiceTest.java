package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceTest {
    private ParserService parserService;

    @Before
    public void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parse_parsingOperationServiceValidData_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(new Fruit("banana"));
        fruitTransaction.setQuantity(10);

        List<FruitTransaction> expectedData = new ArrayList<>();
        expectedData.add(fruitTransaction);
        Class<?> expected = expectedData.get(0).getClass();

        List<String> data = List.of("fruit,quantity","b,banana,10");
        Class<?> actual = parserService.parse(data).get(0).getClass();

        Assert.assertEquals(expected, actual);
    }
}
