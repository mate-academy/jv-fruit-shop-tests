package core.basesyntax.service;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataReaderServiceTest {
    private static final String FILE_NAME = "src/main/resources/data/20221025_0601_ma.csv";
    private static final String NON_EXISTING_PATH = "src/main/resources/data/ma.csv";
    private static DataReaderService fileReader;
    private static List<String> checkList;

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReaderServiceImpl();
        checkList = InitialisationService.getDataList();
    }

    @Test
    public void readFromFile_ok() {
        List<String> dataList = fileReader.readData(FILE_NAME);
        Assert.assertEquals(checkList, dataList);
    }

    @Test(expected = RuntimeException.class)
    public void fileNotExist_notOk() {
        fileReader.readData(NON_EXISTING_PATH);
    }
}
