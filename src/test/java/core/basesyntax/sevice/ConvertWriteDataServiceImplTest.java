package core.basesyntax.sevice;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConvertWriteDataServiceImplTest {
    private static ConvertWriteDataService convertWriteDataService;
    private static String expectedString;

    @BeforeClass
    public static void beforeClass() {
        convertWriteDataService = new ConvertWriteDataServiceImpl();
        expectedString = "fruit,quantity\r\nbanana,10\r\napple,20";
        Storage.fruitStorage.clear();
        Storage.fruitStorage.put("banana", 10);
        Storage.fruitStorage.put("apple", 20);
    }

    @Test
    public void convertDataToFile_validData_Ok() {
        String actual = convertWriteDataService.convertDataToFile(Storage.fruitStorage);
        assertEquals(expectedString, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}
