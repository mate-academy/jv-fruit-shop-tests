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
    private static List<FruitModel> fruitModelsList;
    private static ParserService parserService;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static List<String> csvData;

    @BeforeClass
    public static void beforeAll() {
        csvData = new ArrayList<>();
        fruitModelsList = new ArrayList<>();
        parserService = new ParserServiceImpl();
    }

    @Before
    public void init() {
        csvData = Stream.of("type,fruit,quantity", "b,banana,20", "b,apple,100", "s,banana,100",
                        "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50")
                .collect(Collectors.toList());
        fruitModelsList = Stream.of(
                        new FruitModel(FruitModel.Operation.BALANCE, BANANA, 20),
                        new FruitModel(FruitModel.Operation.BALANCE, APPLE, 100),
                        new FruitModel(FruitModel.Operation.SUPPLY, BANANA, 100),
                        new FruitModel(FruitModel.Operation.PURCHASE, BANANA, 13),
                        new FruitModel(FruitModel.Operation.RETURN, APPLE, 10),
                        new FruitModel(FruitModel.Operation.PURCHASE, APPLE, 20),
                        new FruitModel(FruitModel.Operation.PURCHASE, BANANA, 5),
                        new FruitModel(FruitModel.Operation.SUPPLY, BANANA, 50))
                .collect(Collectors.toList());
    }

    @Test
    public void parseService_ok() {
        List<FruitModel> parseResult = parserService.parseData(csvData);
        assertEquals(fruitModelsList.size(), parseResult.size());

        FruitModel actual;
        FruitModel expected;
        for (int i = 0; i < fruitModelsList.size(); i++) {
            actual = parseResult.get(i);
            expected = fruitModelsList.get(i);
            assertEquals(expected.getFruit(), actual.getFruit());
            assertEquals(expected.getOperation(), actual.getOperation());
            assertEquals(expected.getQuantity(), actual.getQuantity());
        }
    }

    @Test(expected = RuntimeException.class)
    public void stringInvalid_notOk() {
        csvData.add("anything_but_right123&^&)");
        parserService.parseData(csvData);
    }

    @Test(expected = RuntimeException.class)
    public void stringIsEmpty_notOk1() {
        csvData.add("");
        parserService.parseData(csvData);
    }

    @Test(expected = RuntimeException.class)
    public void stringIsNull_notOk() {
        csvData.add(null);
        parserService.parseData(csvData);
    }

    @Test(expected = RuntimeException.class)
    public void invalidFruit_notOk() {
        csvData.add("b, kebab, 12");
        parserService.parseData(csvData);
    }

    @Test(expected = RuntimeException.class)
    public void invalidOperation_notOk() {
        csvData.add("o, banana, 12");
        parserService.parseData(csvData);
    }

    @After
    public void clear() {
        fruitModelsList.clear();
        csvData.clear();
    }
}
