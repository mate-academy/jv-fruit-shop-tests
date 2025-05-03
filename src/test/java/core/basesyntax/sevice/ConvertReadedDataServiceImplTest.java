package core.basesyntax.sevice;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConvertReadedDataServiceImplTest {
    private static ConvertReadedDataService convertReadedDataService;
    private static List<String> inputDataList;
    private static List<FruitTransaction> expectedList;

    @BeforeClass
    public static void beforeClass() {
        convertReadedDataService = new ConvertReadedDataServiceImpl();
        inputDataList = new ArrayList<>();
        inputDataList.add("type,fruit,quantity");
        inputDataList.add("s,apple,13");
        inputDataList.add("s,banana,50");
        expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction("s", "apple", 13));
        expectedList.add(new FruitTransaction("s", "banana", 50));
    }

    @Test
    public void convertDataFromFile_validData_Ok() {
        List<FruitTransaction> resultList
                = convertReadedDataService.convertDataFromFile(inputDataList);
        assertEquals(expectedList, resultList);
    }
}
