package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.StorageService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplementationTest {
    private static final String REPORT_FILE_PATH =
            "src/test/resources/resources/fileWriterTestFile.csv";
    private static StorageService storageService;
    private static FileWriterService fileWriterService;
    private static FileReaderService fileReaderService;
    private static List<String> expected;
    private static List<String> actual;

    @BeforeClass
    public static void beforeClass() {
        storageService = new StorageServiceImpl();
        fileWriterService = new FileWriterImplementation();
        fileReaderService = new FileReaderImpl();
        expected = new ArrayList<>();
        actual = new ArrayList<>();
    }

    @Test
    public void writeDataToFile_Ok() {
        String data = "line1";
        fileWriterService.write(data,REPORT_FILE_PATH);
        expected.add("line1");
        actual = fileReaderService.readFromFile(REPORT_FILE_PATH);
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void writeWithWrongFilePath_NotOk() {
        fileWriterService.write("","");
    }
}
