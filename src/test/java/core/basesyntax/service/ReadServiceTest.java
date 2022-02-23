package core.basesyntax.service;

import core.basesyntax.model.FruitDto;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadServiceTest extends Assert {
    private static ReadService readService;
    private static final String testFileOne = "src/test/resources/Input_test1.csv";
    private static final String testFileTwo = "src/test/resources/Input_test2.csv";
    private static final String testFileThree = "src/test/resources/Input_test3.csv";
    private static final List<FruitDto> correctReadData = List.of(new FruitDto("banana",
            "b", Integer.parseInt("200")),
            new FruitDto("apple", "b", Integer.parseInt("100")),
            new FruitDto("banana", "s", Integer.parseInt("50")),
            new FruitDto("banana", "p", Integer.parseInt("133")),
            new FruitDto("apple", "r", Integer.parseInt("104")),
            new FruitDto("apple", "p", Integer.parseInt("29")),
            new FruitDto("banana", "p", Integer.parseInt("51")),
            new FruitDto("banana", "s", Integer.parseInt("50")));

    @BeforeClass
    public static void beforeClass() {
        readService = new ReadService();
    }

    @Test
    public void readData_file_ok() {
        final List<FruitDto> list = readService.readData(testFileOne);
        final int actual = list.size();
        final int excepted = correctReadData.size();
        assertEquals(excepted, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readData_emptyFile_notOk() {
        final List<FruitDto> data = readService.readData(testFileTwo);
        final int excepted = 0;
        final int actual = data.size();
        assertEquals(excepted, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readData_invalidContent_notOk() {
        final List<FruitDto> data = readService.readData(testFileThree);
        final int excepted = 6;
        final int actual = data.size();
        assertEquals(excepted, actual);
    }
}
