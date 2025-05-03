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
    public void parse_validInputData_SameClassAsExpected() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(new Fruit("banana"));
        fruitTransaction.setQuantity(10);

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(fruitTransaction);

        List<String> data = List.of("fruit,quantity", "b,banana,10");
        List<FruitTransaction> actual = parserService.parse(data);

        Assert.assertEquals(expected, actual);
    }
}
