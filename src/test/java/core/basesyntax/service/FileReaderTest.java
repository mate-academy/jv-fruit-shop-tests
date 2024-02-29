package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

public class FileReaderTest {
    private static final String pathToTestFile = "src/test/resources/test.txt";
    private static final FileReader fileReader = new FileReaderImpl();

    @BeforeEach
    public void createTestFile() {
        Path filePath = Path.of(pathToTestFile);
        try {
            Files.deleteIfExists(filePath);
            Files.createFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void readLines_emptyFile_Ok() {
        try {
            List<String> expected = Files.readAllLines(Path.of(pathToTestFile));
            assertEquals(expected, fileReader.readLines(pathToTestFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void readLines_badFilePath_notOk() {
        String wrongPath = "test/test/fileTest.txt";
        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> fileReader.readLines(wrongPath));
        String expectedErrorMessage = "Can't read file with provided path: " + wrongPath;
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    public void readLines_nullFilePath_notOk(String inputPath) {
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> fileReader.readLines(inputPath));
        String expectedErrorMessage = "Path can't be null";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource
    public void readLines_multipleLines_ok(String content) {
        Path pathToTestFile = Path.of(FileReaderTest.pathToTestFile);
        try {
            List<String> expected = Files.readAllLines(pathToTestFile);
            assertEquals(expected, fileReader.readLines(FileReaderTest.pathToTestFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<String> readLines_multipleLines_ok() {
        return Stream.of("Hello"
                        + System.lineSeparator()
                        + " world!",
                        "1" + System.lineSeparator()
                        + "2" + System.lineSeparator()
                        + "3" + System.lineSeparator()
                        + "4");
    }
}
