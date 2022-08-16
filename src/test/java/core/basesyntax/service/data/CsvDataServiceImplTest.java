package core.basesyntax.service.data;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvDataServiceImplTest {
    private static DataService csvDataService;

    @BeforeClass
    public static void beforeClass() {
        csvDataService = new CsvDataServiceImpl();
    }

    @Test
    public void parser_validString_Ok() {
        String rowFromTextFile = "b,banana,20";
        FruitTransaction expectedValue = new FruitTransaction("b", "banana", 20);
        FruitTransaction actualValue = csvDataService.parser(rowFromTextFile);
        Assert.assertEquals("Parser can't parse the string.", expectedValue, actualValue);
    }

    @Test(expected = RuntimeException.class)
    public void parser_nonValidUser_NotOk() {
        String rowFromTextFile = "b,banana20";
        FruitTransaction actualValue = csvDataService.parser(rowFromTextFile);
    }

    @Test(expected = RuntimeException.class)
    public void parser_EmptyString_NotOk() {
        String rowFromTextFile = "";
        FruitTransaction actualValue = csvDataService.parser(rowFromTextFile);
    }

    @Test
    public void processData_validList_Ok() {
        List<String> paramList = List.of("type,fruit,quantity",
                "b,banana,20","b,apple,100","s,banana,10");
        List<FruitTransaction> expectedValue =
                List.of(new FruitTransaction("b", "banana", 20),
                        new FruitTransaction("b", "apple", 100),
                        new FruitTransaction("s", "banana", 10)
                        );
        List<FruitTransaction> actualValue = csvDataService.processData(paramList);
        Assert.assertEquals("String of the activities were not processed.",
                expectedValue, actualValue);
    }

    @Test(expected = RuntimeException.class)
    public void processData_invalidList_NotOk() {
        List<String> paramList = List.of("type,fruit,quantity",
                "b,banana,r0","b,apple,100","s,banana,10");
        List<FruitTransaction> actualValue = csvDataService.processData(paramList);
    }

    @Test
    public void processData_emptyList_Ok() {
        List<String> paramList = List.of("type,fruit,quantity");
        int expectedValue = 0;
        int actualValue = csvDataService.processData(paramList).size();
        Assert.assertEquals("The returned list size must be 0.",
                expectedValue, actualValue);
    }
}
