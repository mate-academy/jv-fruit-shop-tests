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
        List<String> testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("b,banana,20");
        testList.add("b,apple,100");
        testList.add("s,banana,100");
        testList.add("p,banana,13");
        testList.add("r,apple,10");
        List<String> dataList = fileCsvReader.readFromFile(FILE_EXIST);
        Assert.assertEquals(dataList, testList);
    }

}
