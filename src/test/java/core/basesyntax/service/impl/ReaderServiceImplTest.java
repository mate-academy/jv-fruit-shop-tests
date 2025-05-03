package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String WRONG_FILE_PATH = "src/test/resources/notExistingFile.cvs";
    private static final String INPUT = "src/test/resources/input/input.cvs";
    private static ReaderService service;
    private static List<String> data;

    @BeforeClass
    public static void setUp() {
        service = new ReaderServiceImpl();
        data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,20");
        data.add("b,apple,100");
        data.add("s,banana,100");
        data.add("p,banana,13");
        data.add("r,apple,10");
        data.add("p,apple,20");
        data.add("p,banana,5");
        data.add("s,banana,50");
    }

    @Test(expected = RuntimeException.class)
    public void readData_fileNameIsNull_notOk() {
        service.readData(null);
        fail("You should throw RuntimeException when fileName is null");
    }

    @Test(expected = RuntimeException.class)
    public void readData_fileNameIsNotExisted_notOk() {
        service.readData(WRONG_FILE_PATH);
        fail("You should throw RuntimeException when fileName is not existed");
    }

    @Test
    public void readData_isNotEmpty_ok() {
        List<String> actualList = service.readData(INPUT);
        int actualSize = actualList.size();
        assertEquals("Expected size = " + data.size() + " but was " + actualSize,
                data.size(), actualSize);
        assertEquals("Expected list: " + data.toString() + "but was " + actualList,
                data, actualList);
    }
}
