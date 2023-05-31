package core.basesyntax.service;

import core.basesyntax.service.impl.FileReaderCsvImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static final String VALID_PATH_TO_FILE = "src/main/resources/inputFile.csv";
    private static final String NOT_EXISTING_FILE_PATHNAME = "src/test/resources/not_existed.csv";
    private static FileReaderCsvImpl fileReaderCsv;

    @BeforeClass
    public static void beforeClass() {
        fileReaderCsv = new FileReaderCsvImpl();
    }

    @Test
    public void readFile_withData_ok() {
        List<String> dataFromFileExpected = fillExpectedList();
        List<String> dataFromFileActual = fileReaderCsv.readFile(VALID_PATH_TO_FILE);

        Assert.assertEquals(dataFromFileExpected.size(), dataFromFileActual.size());
        Assert.assertEquals(dataFromFileExpected, dataFromFileActual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_inValidPath_notOk() {
        fileReaderCsv.readFile(NOT_EXISTING_FILE_PATHNAME);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_nullValue_notOk() {
        fileReaderCsv.readFile(null);
    }

    private List<String> fillExpectedList() {
        List<String> dataFromFileExpected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        return dataFromFileExpected;
    }
}
