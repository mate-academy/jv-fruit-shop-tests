package core.basesyntax.util.filereader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String VALID_FILE_NAME = "testFile.csv";
    private static final String EMPTY_FILE_NAME = "emptyFile.csv";
    private static final String NON_EXISTENT_FILE_NAME = "nonExistentFile.csv";
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_validFile_ok() throws Exception {

        Path filePath = Path.of(getClass()
                .getClassLoader()
                .getResource(VALID_FILE_NAME).toURI());

        List<String> expectedLines = Files.readAllLines(filePath);
        List<String> actualLines = fileReader.read(VALID_FILE_NAME);

        Assertions.assertEquals(expectedLines, actualLines,
                "The content read from the file does not match the expected content.");
    }

    @Test
    public void read_fileNotFound_notOk() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () ->
                fileReader.read(NON_EXISTENT_FILE_NAME));

        String expectedMessage = "File not found: " + NON_EXISTENT_FILE_NAME;
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage,
                "The exception message should indicate the file was not found.");
    }

    @Test
    public void read_emptyFile_ok() throws Exception {
        Path filePath = Path.of(getClass()
                .getClassLoader()
                .getResource(EMPTY_FILE_NAME).toURI());

        List<String> expectedLines = Files.readAllLines(filePath);
        List<String> actualLines = fileReader.read(EMPTY_FILE_NAME);

        Assertions.assertEquals(expectedLines, actualLines,
                "The content read from an empty file should be an empty list.");
    }
}
