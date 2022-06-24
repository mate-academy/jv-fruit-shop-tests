package core.basesyntax.service;

import core.basesyntax.service.impl.FileWriterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String TEST_FILE_FOR_WRITER = "src/test/resources/FileForWriterTest.csv";
    private static final String WRONG_FILE_FOR_WRITER = "src/test/resoursec/FileForWriterTest.csv";
    private static FileWriterService fileWriterService;
    private static List<String> dataForTest;

    @Before
    public void setUp() {
        fileWriterService = new FileWriterImpl();
        dataForTest = new ArrayList<>();
    }

    @Test(expected = RuntimeException.class)
    public void writeToNullPath_notOk() {
        fileWriterService.writeToFile(null, dataForTest);
    }

    @Test(expected = RuntimeException.class)
    public void writeToWrongPath_notOk() {
        fileWriterService.writeToFile(WRONG_FILE_FOR_WRITER, dataForTest);
    }

    @Test(expected = RuntimeException.class)
    public void writeNullAsData_notOk() {
        fileWriterService.writeToFile(TEST_FILE_FOR_WRITER, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeEmptyData_notOk() {
        fileWriterService.writeToFile(TEST_FILE_FOR_WRITER, dataForTest);
    }

    @Test
    public void writeDataToFile_isOk() {
        dataForTest.add("s,mango,20");
        fileWriterService.writeToFile(TEST_FILE_FOR_WRITER, dataForTest);
    }
}
