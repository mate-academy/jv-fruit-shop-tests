package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReaderService;

class ReaderServiceImplTest {
    private static final String TEST_FILE = "src/main/resources/test.csv";
    private ReaderService reader;
    private Path testFilePath;

    @BeforeEach
    void setUp() {
        reader = new ReaderServiceImpl();
        try {
            testFilePath = Files.createTempFile("test", ".csv");
        } catch (IOException e) {
            throw new RuntimeException("Can't create file with path " + testFilePath, e);
        }

        String testString = "operation,fruit,quantity\nb,banana,10\nb,apple,20\nb,orange,30";
        try {
            Files.write(testFilePath, testString.getBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + testFilePath, e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(Path.of(TEST_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't find file" + TEST_FILE, e);
        }
        try {
            Files.deleteIfExists(testFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file " + testFilePath, e);
        }
    }

    @Test
    void readFile_ok() {
        List<String> expected = new ArrayList<>(
                Arrays.asList("b,banana,10", "b,apple,20", "b,orange,30")
        );
        List<String> actual = reader.read(String.valueOf(testFilePath));
        assertEquals(expected, actual, "Actual and expected strings must be equals");
    }

    @Test
    void readWrongFilePath_notOk() {
        String fileName = "src/main/resources/wrong.csv";
        assertThrows(RuntimeException.class, () -> reader.read(fileName),
                "Can't find file with such name " + fileName);
    }
}
