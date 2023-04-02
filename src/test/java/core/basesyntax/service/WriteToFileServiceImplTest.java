package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReadFromFileServiceImpl;
import core.basesyntax.service.impl.WriteToFileServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteToFileServiceImplTest {
    private static final String TEST_FILE_PATH = "src/test/resources/WriteToFileTestFile.csv";
    private static WriteToFileService writeToFileService;
    private static ReadFromFileService readFromFileService;

    @BeforeClass
    public static void setUp() {
        writeToFileService = new WriteToFileServiceImpl();
        readFromFileService = new ReadFromFileServiceImpl();
    }

    @Test
    public void writeToFile_checkIfCorrect_isOk() {
        writeToFileService.writeToFile("test text", TEST_FILE_PATH);

        assertEquals("test text", readFromFileService.readFromFile(TEST_FILE_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_IncorrectPath_isNotOk() {
        writeToFileService.writeToFile("test", "");
    }
}
