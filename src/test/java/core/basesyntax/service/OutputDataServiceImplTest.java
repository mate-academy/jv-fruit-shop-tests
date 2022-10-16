package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OutputDataServiceImplTest {
    private static OutputDataService outputDataService;

    @BeforeClass
    public static void beforeClass() {
        outputDataService = new OutputDataServiceImpl();
    }

    @Test
    public void toStringConverter_convertCorrectData_ok() {
        Storage.getFruits().clear();
        Map<String, Integer> testStorageData = Map.of("apple", 10,
                "banana", 20,
                "orange", 0);
        Storage.getFruits().putAll(testStorageData);
        String expected = "apple,10" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "orange,0" + System.lineSeparator();
        String actual = outputDataService.toStringConverter();
        assertEquals(expected, actual);
    }

    @Test
    public void toStringConverter_convertEmptyData_ok() {
        Storage.getFruits().clear();
        String expected = "";
        assertEquals(expected, outputDataService.toStringConverter());
    }

    @Test
    public void toStringConverter_convertNullValuesData_notOk() {
        Storage.getFruits().clear();
        Storage.getFruits().put("banana", null);
        Storage.getFruits().put("apple", 50);
        try {
            outputDataService.toStringConverter();
            fail("Exception not thrown");
        } catch (NullPointerException e) {
            assertEquals("Can't get data from the storage, because "
                            + "banana" + " has \"null\" value",
                    e.getMessage());
        }
    }
}
