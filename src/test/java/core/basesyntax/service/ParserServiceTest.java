package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitModel;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceTest {
    private static final FruitModel.Operation BALANCE = FruitModel.Operation.BALANCE;
    private static final FruitModel.Operation PURCHASE = FruitModel.Operation.PURCHASE;
    private static final FruitModel.Operation SUPPLY = FruitModel.Operation.SUPPLY;
    private static final FruitModel.Operation RETURN = FruitModel.Operation.RETURN;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static List<String> list;
    private static List<FruitModel> fruitModels;
    private static ParserService parserService;

    @BeforeClass
    public static void beforeAll() {
        list = new ArrayList<>();
        fruitModels = new ArrayList<>();
        parserService = new ParserServiceImpl();
    }

    @Before
    public void init() {
        list = Stream.of("type,fruit,quantity", "b,banana,20", "b,apple,100", "s,banana,100",
                        "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50")
                .collect(Collectors.toList());
        fruitModels = Stream.of(
                        new FruitModel(BALANCE, BANANA, 20),
                        new FruitModel(BALANCE, APPLE, 100),
                        new FruitModel(SUPPLY, BANANA, 100),
                        new FruitModel(PURCHASE, BANANA, 13),
                        new FruitModel(RETURN, APPLE, 10),
                        new FruitModel(PURCHASE, APPLE, 20),
                        new FruitModel(PURCHASE, BANANA, 5),
                        new FruitModel(SUPPLY, BANANA, 50))
                .collect(Collectors.toList());
    }

    @Test
    public void parseService_ok() {
        List<FruitModel> actualParseService = parserService.parseData(list);
        assertEquals(fruitModels.size(), actualParseService.size());

        FruitModel actual;
        FruitModel expected;
        for (int i = 0; i < fruitModels.size(); i++) {
            actual = actualParseService.get(i);
            expected = fruitModels.get(i);
            assertEquals(expected.getFruit(), actual.getFruit());
            assertEquals(expected.getOperation(), actual.getOperation());
            assertEquals(expected.getQuantity(), actual.getQuantity());
        }
    }

    @Test(expected = RuntimeException.class)
    public void stringInvalid_notOk() {
        list.add("anything_but_right123&^&)");
        parserService.parseData(list);
    }

    @Test(expected = RuntimeException.class)
    public void stringIsEmpty_notOk1() {
        list.add("");
        parserService.parseData(list);
    }

    @Test(expected = RuntimeException.class)
    public void stringIsNull_notOk() {
        list.add(null);
        parserService.parseData(list);
    }

    @Test(expected = RuntimeException.class)
    public void invalidFruit_notOk() {
        list.add("b, kebab, 12");
        parserService.parseData(list);
    }

    @Test(expected = RuntimeException.class)
    public void invalidOperation_notOk() {
        list.add("o, banana, 12");
        parserService.parseData(list);
    }

    @After
    public void clear() {
        fruitModels.clear();
        list.clear();
    }
}
