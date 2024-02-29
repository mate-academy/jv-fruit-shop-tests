package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class FileWriterTest {
    private static final String pathToTestFile = "src/test/resources/test.txt";
    private static final FileWriter fileWriter = new FileWriterImpl();

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
    public void writeToFile_badFilePath_notOk() {
        String wrongFilePath = "some/wrong/path";
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> fileWriter.writeToFile(
                                "Test Line", wrongFilePath));
        String expected = "Can't write to file: " + wrongFilePath;
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void writeToFile_nullFilePath_notOk() {
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> fileWriter.writeToFile(
                                "Test Line", null));
        String expectedMessage = "Path can't be null";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void writeToFile_nullString_notOk() {
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> fileWriter.writeToFile(
                                null, pathToTestFile));
        String expectedMessage = "Can't write null value to file";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void writeToFile_emptyString_ok() {
        fileWriter.writeToFile("", pathToTestFile);
        try {
            List<String> strings = Files.readAllLines(Path.of(pathToTestFile));
            assertTrue(strings.isEmpty());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @MethodSource
    public void writeToFile_multipleLines_Ok(String content) {
        fileWriter.writeToFile(content, pathToTestFile);
        try {
            List<String> actualList = Files.readAllLines(Path.of(pathToTestFile));
            String actual = actualList.stream().collect(Collectors.joining(System.lineSeparator()));
            assertEquals(content, actual);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<String> writeToFile_multipleLines_Ok() {
        return Stream.of("Hello"
                        + System.lineSeparator()
                        + " world!",
                "1" + System.lineSeparator()
                        + "2" + System.lineSeparator()
                        + "3" + System.lineSeparator()
                        + "4");
    }
}
