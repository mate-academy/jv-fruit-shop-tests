package core.basesyntax.service.implementations;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileServiceImplTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/dayInStore.csv";
    private static final String INPUT_FILE_WRONG_PATH = "src/test/dayInStore.csv";
    private static final String OUTPUT_FILE_PATH = "src/test/resources/outputTestFile.csv";
    private static final String OUTPUT_FILE_WRONG_PATH = "src/test/resourcesss/outputTestFile.csv";
    private static final String TEST_STRING = "test string text";
    private FileService fileService;

    @BeforeAll
    public static void beforeAll() throws Exception {
        Files.write(Path.of(INPUT_FILE_PATH), (
                "type,fruit,quantity\n"
                        + "b,banana,5\n"
                        + "b,apple,110\n"
                        + "s,banana,100\n"
                        + "p,banana,12\n"
                        + "r,apple,10\n"
                        + "p,apple,20\n"
                        + "p,banana,5\n"
                        + "s,banana,50\n").getBytes(StandardCharsets.UTF_8));
    }

    @BeforeEach
    void setUp() {
        fileService = new FileServiceImpl();
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
        Assertions.assertEquals(TEST_STRING, actual);
    }

    @Test
    public void writeToFile_nullPath_notOk() {
        assertThrows(NullPointerException.class, () -> fileService.writeToFile(TEST_STRING, null));
    }

    @Test
    public void writeToFile_wrongPath_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileService.writeToFile(TEST_STRING, OUTPUT_FILE_WRONG_PATH));
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
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void readFromFile_fileAbsent_notOk() {
        assertThrows(RuntimeException.class,() -> fileService.readFromFile(INPUT_FILE_WRONG_PATH));
    }

    @Test
    public void readFromFile_nullPath_notOk() {
        assertThrows(NullPointerException.class,() -> fileService.readFromFile(null));
    }

    @AfterAll
    static void afterAll() {
        try {
            Files.deleteIfExists(Path.of(OUTPUT_FILE_PATH));
            Files.deleteIfExists(Path.of(INPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't clear result files after test ", e);
        }
    }
}
