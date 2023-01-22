package core.basesyntax.service.implementations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/dayInStore.csv";
    private static final String INPUT_FILE_WRONG_PATH = "src/test/dayInStore.csv";
    private static final String OUTPUT_FILE_PATH = "src/test/resources/outputTestFile.csv";
    private static final String OUTPUT_FILE_WRONG_PATH = "src/test/resourcesss/outputTestFile.csv";
    private static final String TEST_STRING = "test string text";
    private FileService fileService = new FileServiceImpl();

    @Test
    public void writeToFile_ok() {
        fileService.writeToFile(TEST_STRING, OUTPUT_FILE_PATH);
        String actual;
        try {
            actual = Files.readString(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read string from " + OUTPUT_FILE_PATH, e);
        }
        assertEquals(TEST_STRING, actual);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullPath_notOk() {
        fileService.writeToFile(TEST_STRING, null);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_wrongPath_notOk() {
        fileService.writeToFile(TEST_STRING, OUTPUT_FILE_WRONG_PATH);
    }

    @Test
    public void readFromFile_ok() {
        List<String> expectedResult = new ArrayList<>(
                List.of("type,fruit,quantity",
                        "b,banana,5",
                        "b,apple,110",
                        "s,banana,100",
                        "p,banana,12",
                        "r,apple,10",
                        "p,apple,20",
                        "p,banana,5",
                        "s,banana,50"));
        List<String> actualResult = fileService.readFromFile(INPUT_FILE_PATH);
        assertEquals(expectedResult, actualResult);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_fileAbsent_notOk() {
        fileService.readFromFile(INPUT_FILE_WRONG_PATH);
    }

    @Test (expected = NullPointerException.class)
    public void readFromFile_nullPath_notOk() {
        fileService.readFromFile(null);
    }

    @AfterClass
    public static void afterClass() {
        try {
            Files.deleteIfExists(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't clear result files after test ", e);
        }
    }
}
