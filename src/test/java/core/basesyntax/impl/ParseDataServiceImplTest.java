package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.servise.ParseDataService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseDataServiceImplTest {
    private static ParseDataService parseDataService;

    @BeforeClass
    public static void setUp() {
        parseDataService = new ParseDataServiceImpl();
    }

    @Test
    public void parseValidInputData_ok() {
        List<String> dataFromFile = new ArrayList<>();
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("b,banana,100");
        dataFromFile.add("s,apple,50");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("banana"), 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("apple"), 50));
        List<FruitTransaction> actual = parseDataService.parse(dataFromFile);
        assertEquals(expected, actual);
    }

    @Test
    public void parseEmptyData_ok() {
        List<FruitTransaction> expected = List.of();
        List<FruitTransaction> actual = parseDataService.parse(List.of());
        assertEquals(expected, actual);
    }
}
