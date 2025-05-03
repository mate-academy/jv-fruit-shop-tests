package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ParseDataService;
import core.basesyntax.service.impl.ParseDataServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseDataServiceTest {
    private static ParseDataService parseDataService;

    @BeforeClass
    public static void initialSetup() {
        parseDataService = new ParseDataServiceImpl();
    }

    @Test
    public void parseData_emptyDataFromFile_ok() {
        List<String> dataFromFile = new ArrayList<>();
        List<Fruit> result = parseDataService.parseData(dataFromFile);
        List<Fruit> expected = new ArrayList<>();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void parseData_valid_ok() {
        List<String> dataFromFile = new ArrayList<>();
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("b,banana,20");
        dataFromFile.add("b,apple,100");
        dataFromFile.add("s,banana,100");
        dataFromFile.add("p,banana,13");
        dataFromFile.add("r,apple,10");
        dataFromFile.add("p,apple,20");
        dataFromFile.add("p,banana,5");
        dataFromFile.add("s,banana,50");
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit(Operation.BALANCE, "banana", 20));
        expected.add(new Fruit(Operation.BALANCE, "apple", 100));
        expected.add(new Fruit(Operation.SUPPLY, "banana", 100));
        expected.add(new Fruit(Operation.PURCHASE, "banana", 13));
        expected.add(new Fruit(Operation.RETURN, "apple", 10));
        expected.add(new Fruit(Operation.PURCHASE, "apple", 20));
        expected.add(new Fruit(Operation.PURCHASE, "banana", 5));
        expected.add(new Fruit(Operation.SUPPLY, "banana", 50));
        List<Fruit> result = parseDataService.parseData(dataFromFile);
        Assert.assertEquals(expected, result);
    }
}
