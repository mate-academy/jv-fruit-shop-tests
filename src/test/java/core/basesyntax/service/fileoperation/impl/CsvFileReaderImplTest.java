package core.basesyntax.service.fileoperation.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.fileoperation.CsvFileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderImplTest {
    private static final Path TEST_FILE_PATH = Path.of("src/test/resources.csv");
    private static final Path INVALID_TEST_FILE_PATH = Path.of("src/test/invalidResources.csv");
    private static CsvFileReader reader;
    private static List<String> validTestData;
    private static List<String> emptyTestData;

    @After
    public void afterClass() throws IOException {
        Files.delete(TEST_FILE_PATH);
    }

    @BeforeClass
    public static void setUp() {
        reader = new CsvFileReaderImpl();
        validTestData = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "b,banana,100",
                "b,grapefruit,100",
                "r,banana,1",
                "s,apple,10",
                "p,orange,10",
                "s,grapefruit,10",
                "r,orange,3");
        emptyTestData = List.of("type,fruit,quantity");
    }

    @Test
    public void readInputFile_validFile_Ok() {
        createTestFile(getStringForWrite(validTestData));
        List<String> actual = reader.inputFile(TEST_FILE_PATH.toString());
        assertEquals(validTestData, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readInputFile_emptyFile_notOk() {
        createTestFile(getStringForWrite(emptyTestData));
        reader.inputFile(TEST_FILE_PATH.toString());
    }

    @Test(expected = RuntimeException.class)
    public void readInputFile_invalidFilePath_notOk() {
        createTestFile(getStringForWrite(validTestData));
        reader.inputFile(INVALID_TEST_FILE_PATH.toString());
    }

    private void createTestFile(String path) {
        try {
            Files.write(TEST_FILE_PATH, path.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Cant write data to file " + TEST_FILE_PATH, e);
        }
    }

    private String getStringForWrite(List<String> data) {
        return data.stream()
                .map(s -> s + System.lineSeparator())
                .collect(Collectors.joining());
    }
}
