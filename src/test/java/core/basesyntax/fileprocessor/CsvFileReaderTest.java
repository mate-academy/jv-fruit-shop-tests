package core.basesyntax.fileprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileReaderTest {

    private static String VALID_FILE_PATH = "test_valid_file.csv";
    private static String EMPTY_FILE = "empty_test_file.csv";
    private static String NON_EXISTENT_FILE = "non_existent_file.csv";
    private FileReader fileReader;
    private CsvFileReader csvFileReader;

    @BeforeEach
    void setUp() throws IOException {
        csvFileReader = new CsvFileReader();
        fileReader = new FileReader();
        Path validFilePath = Path.of(VALID_FILE_PATH);
        Files.write(validFilePath, List.of("b,banana,10", "s,apple,20", "p,orange,5"));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(EMPTY_FILE));
        Files.deleteIfExists(Path.of(NON_EXISTENT_FILE));
    }

    @Test
    void read_validFile_returnsContent() {
        List<String> result = csvFileReader.read(VALID_FILE_PATH);
        assertEquals(3, result.size());
        assertEquals("b,banana,10", result.get(0));
        assertEquals("s,apple,20", result.get(1));
        assertEquals("p,orange,5", result.get(2));
    }

    @Test
    void read_emptyFile_returnsEmptyList() throws IOException {
        String emptyFilePath = EMPTY_FILE;
        Files.createFile(Path.of(emptyFilePath));
        List<String> result = csvFileReader.read(emptyFilePath);
        assertEquals(0, result.size());
    }
}
