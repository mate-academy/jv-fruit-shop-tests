package core.basesyntax.service.impl;

import core.basesyntax.exception.NoSuchOperationException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserServiceImplTest {
    private static DataParserService parserService;
    private static List<String> parseInput;
    private static List<FruitTransaction> expectedList;
    private static final int COUNT_FIRST_BANANA = 20;
    private static final int COUNT_SECOND_APPLE = 100;
    private static final int COUNT_THIRD_BANANA = 13;
    private static final int COUNT_FOURTH_APPLE = 10;

    @BeforeClass
    public static void init() {
        parserService = new FruitTransactionParserServiceImpl();
        FruitTransaction first = new FruitTransaction();
        first.setFruit(new Fruit("banana"));
        first.setQuantity(COUNT_FIRST_BANANA);
        first.setOperation(FruitTransaction.Operation.BALANCE);

        FruitTransaction second = new FruitTransaction();
        second.setFruit(new Fruit("apple"));
        second.setQuantity(COUNT_SECOND_APPLE);
        second.setOperation(FruitTransaction.Operation.BALANCE);

        FruitTransaction third = new FruitTransaction();
        third.setFruit(new Fruit("banana"));
        third.setQuantity(COUNT_THIRD_BANANA);
        third.setOperation(FruitTransaction.Operation.PURCHASE);

        FruitTransaction fourth = new FruitTransaction();
        fourth.setFruit(new Fruit("apple"));
        fourth.setQuantity(COUNT_FOURTH_APPLE);
        fourth.setOperation(FruitTransaction.Operation.RETURN);

        expectedList = List.of(first, second, third, fourth);
    }

    @Before
    public void setParseInput() {
        parseInput = new ArrayList<>();
        parseInput.add("type,fruit,quantity");
        parseInput.add("b,banana,20");
        parseInput.add("b,apple,100");
        parseInput.add("p,banana,13");
        parseInput.add("r,apple,10");
    }

    @Test
    public void parse_correctInput_ok() {
        List<FruitTransaction> actual = parserService.parse(parseInput);
        for (int i = 0; i < expectedList.size(); i++) {
            Assert.assertEquals(expectedList.get(i).getFruit(), actual.get(i).getFruit());
            Assert.assertEquals(expectedList.get(i).getQuantity(), actual.get(i).getQuantity());
            Assert.assertEquals(expectedList.get(i).getOperation(), actual.get(i).getOperation());
        }
    }

    @Test(expected = NullPointerException.class)
    public void parse_nullInput_notOk() {
        parserService.parse(null);
    }

    @Test
    public void parse_emptyListInput_ok() {
        Assert.assertEquals(Collections.emptyList(), parserService.parse(Collections.emptyList()));
    }

    @Test(expected = NoSuchOperationException.class)
    public void parse_inputWithNotFoundOperation_notOk() {
        parseInput.add("?,apple,10");
        parserService.parse(parseInput);
    }

    @Test(expected = NullPointerException.class)
    public void parse_inputWithNullLine_notOk() {
        parseInput.add(null);
        parserService.parse(parseInput);
    }
}
