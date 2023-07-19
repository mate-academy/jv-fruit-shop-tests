package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseDataService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParseDataServiceImplTest {
    private ParseDataService parseDataService;

    @Before
    public void setUp() {
        parseDataService = new ParseDataServiceImpl();
    }

    @Test
    public void parseData_Ok() {
        List<String> list = new ArrayList<>(List.of("type,fruit,quantity", "b,banana,10",
                "s,orange,20", "r,pie,30", "p,apple,40", "b,kiwi,50"));
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("banana"), 10));
        expected.add(new FruitTransaction("s", new Fruit("orange"), 20));
        expected.add(new FruitTransaction("r", new Fruit("pie"), 30));
        expected.add(new FruitTransaction("p", new Fruit("apple"), 40));
        expected.add(new FruitTransaction("b", new Fruit("kiwi"), 50));
        List<FruitTransaction> actual = parseDataService.parseData(list);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseData_FromEmptyList_NotOk() {
        List<FruitTransaction> actual = parseDataService.parseData(Collections.emptyList());
    }
}
