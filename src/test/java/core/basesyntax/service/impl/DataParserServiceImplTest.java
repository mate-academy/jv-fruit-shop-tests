package core.basesyntax.service.impl;

import static org.junit.Assert.fail;

import core.basesyntax.model.FruitRecord;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserServiceImplTest {
    private static DataParserServiceImpl dataParserService;

    @BeforeClass
    public static void beforeAll() {
        dataParserService = new DataParserServiceImpl();
    }

    @Test
    public void parseData_Ok() {
        List<String> data = List.of("b,apple,50", "s,banana,250", "p,apple,20", "r,apple,20");
        List<FruitRecord> expected = List.of(
                new FruitRecord(FruitRecord.Operation.BALANCE, "apple", 50),
                new FruitRecord(FruitRecord.Operation.SUPPLY, "banana", 250),
                new FruitRecord(FruitRecord.Operation.PURCHASE, "apple", 20),
                new FruitRecord(FruitRecord.Operation.RETURN, "apple", 20)
        );
        String actual = dataParserService.parseData(data).toString();
        Assert.assertEquals("Data wasn't parsed correctly!", expected.toString(), actual);
    }

    @Test
    public void parseData_NotOk() {
        List<String> data = List.of("b,apple,-60");
        try {
            dataParserService.parseData(data);
        } catch (RuntimeException e) {
            return;
        }
        fail("You should throw an exception for wrong input data!");
    }
}
