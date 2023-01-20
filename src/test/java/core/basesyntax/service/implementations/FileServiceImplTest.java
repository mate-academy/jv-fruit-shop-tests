package core.basesyntax.service.implementations;

import core.basesyntax.service.FileService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FileServiceImplTest {
    private static final String INPUT_FILE_PATH = "src/main/resources/dayInStore.csv";
    private static final String INPUT_FILE_WRONG_PATH = "src/main/dayInStore.csv";
    private static final String OUTPUT_FILE_PATH = "src/main/resources/outputTestFile.csv";
    private static final String OUTPUT_FILE_WRONG_PATH = "src/main/resourcesss/outputTestFile.csv";
    private static final String TEST_STRING = "test string text";
    FileService fileService = new FileServiceImpl();

    @BeforeClass
    public static void beforeClass() throws Exception {
        Files.write(Path.of(INPUT_FILE_PATH), (
                "type,fruit,quantity\n" +
                        "b,banana,20\n" +
                        "b,apple,100\n" +
                        "s,banana,100\n" +
                        "p,banana,13\n" +
                        "r,apple,10\n" +
                        "p,apple,20\n" +
                        "p,banana,5\n" +
                        "s,banana,50\n").getBytes(StandardCharsets.UTF_8));
    }

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

    @Test
    public void writeToFile_nullPath_notOk() {
        assertThrows(NullPointerException.class, () -> fileService.writeToFile(TEST_STRING, null));
    }

    @Test
    public void writeToFile_wrongPath_notOk() {
        assertThrows(RuntimeException.class, () -> fileService.writeToFile(TEST_STRING, OUTPUT_FILE_WRONG_PATH));
    }

    @Test
    public void readFromFile_ok() {
        List<String> readFile;
        readFile = fileService.readFromFile(INPUT_FILE_PATH);
        List<String> expectedResult = new ArrayList<>(
                List.of("type,fruit,quantity",
                        "b,banana,20",
                        "b,apple,100",
                        "s,banana,100",
                        "p,banana,13",
                        "r,apple,10",
                        "p,apple,20",
                        "p,banana,5",
                        "s,banana,50"));
        assertEquals("Test failed. Read data are incorrect", expectedResult, readFile);
    }

    @Test
    public void readFromFile_fileAbsent_notOk() {
        assertThrows(RuntimeException.class,() -> fileService.readFromFile(INPUT_FILE_WRONG_PATH));
    }

    @Test
    public void readFromFile_nullPath_notOk() {
        assertThrows(NullPointerException.class,() -> fileService.readFromFile(null));
    }

    @After
    public void clearResults() {
        try {
            Files.deleteIfExists(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't clear result files after test ", e);
        }
    }
}