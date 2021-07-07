package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static FileService fileService;
    private static final String VALID_FILE_TO_TEST_READ = "src/test/resources/fileToTestRead.csv";
    private static final String NON_EXISTING_FILE_TO_TEST_READ = "src/test/resources/file.csv";
    private static final String FILE_TO_TEST_WRITE = "src/test/resources/fileToTestWrite.csv";

    @BeforeClass
    public static void setFileService() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void readFile_FileExist_isOk() {
        List<String> result = fileService.readFile(VALID_FILE_TO_TEST_READ);
        List<String> expected = List.of("fruit,quantity", "apple,25", "orange,70");
        assertEquals(expected, result);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_FileNotExist_IoException() {
        fileService.readFile(NON_EXISTING_FILE_TO_TEST_READ);
    }

    @Test
    public void writeToFile() {
        String sourceData = "fruit,quantity" + System.lineSeparator() + "apple,25"
                + System.lineSeparator() + "orange,70" + System.lineSeparator();
        fileService.writeToFile(FILE_TO_TEST_WRITE, sourceData);
        List<String> expected = List.of("fruit,quantity", "apple,25", "orange,70");
        List<String> result = fileService.readFile(FILE_TO_TEST_WRITE);
        assertEquals(expected, result);
    }
}
