package core.basesyntax.service;

import core.basesyntax.service.impl.FileCsvReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileCsvReaderImplTest {
    private static final String FILE_EXIST = "src/test/resources/input.csv";
    private static final String FILE_DOES_NOT_EXIST = "src/test/resources/wrong.csv";
    private static FileCsvReader fileCsvReader;

    @BeforeClass
    public static void setUp() {
        fileCsvReader = new FileCsvReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_fileDoesNotExist_notOk() {
        fileCsvReader.readFromFile(FILE_DOES_NOT_EXIST);
    }

    @Test
    public void readFromFile_fileExist_ok() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
        expectedList.add("s,banana,100");
        expectedList.add("p,banana,13");
        expectedList.add("r,apple,10");
        List<String> actualList = fileCsvReader.readFromFile(FILE_EXIST);
        Assert.assertEquals(actualList, expectedList);
    }

}
