package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.NullSource;

public class FileReaderTest {
    private static final String PATH_TO_TEST_FILE = "src/test/resources/test.txt";
    private static final FileReader fileReader = new FileReaderImpl();

    @BeforeEach
    public void createTestFile() {
        Path filePath = Path.of(PATH_TO_TEST_FILE);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void readLines_emptyFile_Ok() {
        try {
            Path pathToTestFile = Path.of(PATH_TO_TEST_FILE);
            Files.createFile(pathToTestFile);
            List<String> expected = Files.readAllLines(pathToTestFile);
            assertEquals(expected, fileReader.readLines(PATH_TO_TEST_FILE));
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
    @ArgumentsSource(ArgumentsProvider.class)
    public void readLines_multipleLines_ok(String content, List<String> expectedList) {
        Path pathToTestFile = Path.of(FileReaderTest.PATH_TO_TEST_FILE);
        try {
            Files.writeString(pathToTestFile, content);
            assertEquals(expectedList, fileReader.readLines(FileReaderTest.PATH_TO_TEST_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ArgumentsProvider
            implements org.junit.jupiter.params.provider.ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of("Hello"
                            + System.lineSeparator()
                            + " world!",
                            List.of("Hello", " world!")),
                    Arguments.of("1" + System.lineSeparator()
                            + "2" + System.lineSeparator()
                            + "3" + System.lineSeparator()
                            + "4",
                            List.of("1", "2", "3", "4")));
        }
    }
}
