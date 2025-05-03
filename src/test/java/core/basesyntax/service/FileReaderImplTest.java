package core.basesyntax.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final int LIST_LENGTH = 3;
    private static final String TEST_FILE = "test_file.txt";
    private FileReader fileReader;

    @BeforeEach
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE));
    }

    @Test
    public void readFile_validFile_ok() throws IOException {
        List<String> testData = List.of("Header", "Line1", "Line2", "Line3");
        List<String> expected = testData.subList(1, testData.size());
        Files.write(Path.of(TEST_FILE), testData);
        List<String> result = fileReader.readFile(TEST_FILE);
        Assertions.assertEquals(LIST_LENGTH, result.size());
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void readFile_fileWithOnlyHeader_Ok() throws IOException {
        List<String> testData = List.of("Header");
        Files.write(Path.of(TEST_FILE), testData);
        List<String> result = fileReader.readFile(TEST_FILE);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
        public void readFile_nonExistingFile_NotOk() {
        Exception exception = assertThrows(RuntimeException.class,
                    () -> fileReader.readFile("non_existing_file.txt"));
        assertInstanceOf(IOException.class, exception.getCause());
    }
}
