package core.basesyntax.service.impl;

import core.basesyntax.exception.CsvFileException;
import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class CsvFileWriterTest {
    private static final String EMPTY_CONTENT = "";
    private static final Path DESTINATION_PATH = Path.of("src/test/resources/destination.csv");
    private static final Path SOURCE_PATH = Path.of("src/test/resources/validSource_0.csv");
    private static String[] directoryPaths;
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new CsvFileWriter();
        directoryPaths = new String[] {"src/test/resources", "src/test/resources/"};
    }

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(DESTINATION_PATH);
    }

    @Test
    void write_pathNull_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write(null, EMPTY_CONTENT),
                "IllegalArgumentException must be thrown when path is null");
    }

    @TestFactory
    Stream<DynamicTest> write_directoryPath_notOk() {
        return Arrays.stream(directoryPaths).map(pathString ->
                DynamicTest.dynamicTest("Test path: \"" + pathString + "\"",
                        () -> Assertions.assertThrows(IllegalArgumentException.class,
                                () -> fileWriter.write(Path.of(pathString), EMPTY_CONTENT),
                                "IllegalArgumentException must be thrown when path is directory")
                ));
    }

    @Test
    void write_contentNull_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> fileWriter.write(DESTINATION_PATH, null),
                "NullPointerException must be thrown if content is null");
        Assertions.assertTrue(Files.notExists(DESTINATION_PATH));
    }

    @Test
    void write_emptyContent_ok() throws IOException {
        fileWriter.write(DESTINATION_PATH, EMPTY_CONTENT);
        Assertions.assertTrue(Files.exists(DESTINATION_PATH));
        String result = Files.readString(DESTINATION_PATH);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void write_largeContent_ok() throws IOException {
        String content = Files.readString(SOURCE_PATH);
        fileWriter.write(DESTINATION_PATH, content);
        Assertions.assertTrue(Files.exists(DESTINATION_PATH));
        String result = Files.readString(DESTINATION_PATH);
        Assertions.assertEquals(content, result);
    }

    @Test
    void write_ioExceptionOccurs_notOk() throws IOException {
        File file = Files.createFile(DESTINATION_PATH).toFile();
        file.setWritable(false);
        Assertions.assertThrows(CsvFileException.class,
                () -> fileWriter.write(DESTINATION_PATH, EMPTY_CONTENT),
                "CsvFileException must be thrown if IOException occurred");
        file.setWritable(true);
    }

}
