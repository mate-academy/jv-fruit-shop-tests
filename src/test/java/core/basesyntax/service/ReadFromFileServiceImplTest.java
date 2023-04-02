package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReadFromFileServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFromFileServiceImplTest {
    private static final String TEST_FILE_PATH = "src/test/resources/readFromFileTestFile.csv";
    private static ReadFromFileService readFromFileService;

    @BeforeClass
    public static void setUp() {
        readFromFileService = new ReadFromFileServiceImpl();
    }

    @Test
    public void readFromFile_checkIfCorrect_isOk() {
        String expected = "test,test,test";

        assertEquals(expected, readFromFileService.readFromFile(TEST_FILE_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_filePathIncorrect_isNotOk() {
        readFromFileService.readFromFile("");
    }
}
