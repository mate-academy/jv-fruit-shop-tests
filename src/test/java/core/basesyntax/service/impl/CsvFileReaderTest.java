package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class CsvFileReaderTest {
    private static final String EMPTY_SOURCE_PATH = "src/test/resources/emptySource.csv";
    private static final String ONLY_HEADER_SOURCE_PATH = "src/test/resources/onlyHeaderSource.csv";
    private static FileReader fileReader;
    private static String[] directorySourcePaths;
    private static String[] validSources;
    private static String[] notExistingPaths;

    @BeforeAll
    static void beforeAll() {
        fileReader = new CsvFileReader();
        directorySourcePaths = new String[]{"src/test/resources", "src/test/resources/"};
        validSources = new String[]{
                "src/test/resources/validSource_0.csv",
                "src/test/resources/validSource_1.csv"};
        notExistingPaths = new String[]{
                "src/test/resources/NOT_EXISTING_PATH",
                "src/test/resources/notExisting.csv",
                "", "NOT_EXISTING"};
    }

    @Test
    void read_nullPath_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> fileReader.read(null),
                "IllegalArgumentException must be thrown if provided path is null");
    }

    @TestFactory
    Stream<DynamicTest> createNewCsvFile_directorySourcePath_notOk() {
        String message = "IllegalArgumentException must be thrown if provided path is directory";
        return Arrays.stream(directorySourcePaths).map(stringPath ->
                DynamicTest.dynamicTest("Test path: \"" + stringPath + "\"",
                        () -> Assertions.assertThrows(IllegalArgumentException.class,
                                () -> fileReader.read(Path.of(stringPath)),
                                message)
                ));
    }

    @TestFactory
    Stream<DynamicTest> createNewCsvFileWriter_notExistingPaths_notOk() {
        return Arrays.stream(notExistingPaths).map(pathString ->
                DynamicTest.dynamicTest("Test path: \"" + pathString + "\"",
                        () -> Assertions.assertThrows(IllegalArgumentException.class,
                                () -> fileReader.read(Path.of(pathString)),
                                "IllegalArgument must be thrown if provided not existing path")
                ));
    }

    @Test
    void read_emptySource_ok() {
        List<String> records = fileReader.read(Path.of(EMPTY_SOURCE_PATH));
        Assertions.assertTrue(records.isEmpty(),
                "Method read() must return empty list if source is empty");
    }

    @Test
    void read_onlyHeaderSource_ok() throws IOException {
        assertFileContentEquals(ONLY_HEADER_SOURCE_PATH);
    }

    @TestFactory
    Stream<DynamicTest> read_validSource_ok() {
        return Arrays.stream(validSources).map(pathString ->
                DynamicTest.dynamicTest("Test path: \"" + pathString + "\"",
                        () -> assertFileContentEquals(pathString)
                ));
    }

    private void assertFileContentEquals(String pathString) throws IOException {
        Path path = Path.of(pathString);
        List<String> expectedContent = Files.readAllLines(path);
        List<String> records = fileReader.read(path);
        Assertions.assertEquals(expectedContent, records,
                "Method must return the same lines as in the source");

    }
}
