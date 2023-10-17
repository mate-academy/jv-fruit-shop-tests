package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidFilePathException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DefaultFileServiceTest {
    private static final String RESOURCES_DIRECTORY = "src/test/resources";
    private static final String EMPTY_INPUT_FILEPATH = "src/test/resources/empty-input.csv";
    private static final String NOT_EMPTY_INPUT_FILEPATH =
            "src/test/resources/not-empty-input.csv";
    private static final String NONEXISTENT_INPUT_FILEPATH =
            "src/test/resources/nonexistent-input.csv";
    private static final String OUTPUT_FILEPATH = "src/test/resources/output.csv";

    private static final String EXAMPLE_DATA = "Hello, I'm the first line" + System.lineSeparator()
            + "I'm the second line :)" + System.lineSeparator()
            + "And I'm the last line";

    private static FileService fileService;

    @BeforeAll
    static void beforeAll() {
        fileService = new DefaultFileService();

        File resourcesDir = new File(RESOURCES_DIRECTORY);
        if (!resourcesDir.exists()) {
            resourcesDir.mkdir();
        }

        try {
            Files.createFile(Path.of(EMPTY_INPUT_FILEPATH));
            Files.write(Path.of(NOT_EMPTY_INPUT_FILEPATH), EXAMPLE_DATA.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Cannot create input files: "
                    + List.of(EMPTY_INPUT_FILEPATH, NOT_EMPTY_INPUT_FILEPATH), e);
        }
    }

    @AfterAll
    static void afterAll() {
        try {
            Files.deleteIfExists(Path.of(EMPTY_INPUT_FILEPATH));
            Files.deleteIfExists(Path.of(NOT_EMPTY_INPUT_FILEPATH));
            Files.deleteIfExists(Path.of(OUTPUT_FILEPATH));
        } catch (IOException e) {
            throw new RuntimeException("Cannot delete files: "
                    + List.of(EMPTY_INPUT_FILEPATH, NOT_EMPTY_INPUT_FILEPATH, OUTPUT_FILEPATH), e);
        }
    }

    @Test
    void readLines_validCase_ok() {
        int actual = fileService.readLines(NOT_EMPTY_INPUT_FILEPATH).size();
        int expected = EXAMPLE_DATA.split(System.lineSeparator()).length;
        assertEquals(expected, actual);
    }

    @Test
    void readLines_emptyFile_ok() {
        int actual = fileService.readLines(EMPTY_INPUT_FILEPATH).size();
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void readLines_nonexistentFile_notOk() {
        assertThrows(
                InvalidFilePathException.class,
                () -> fileService.readLines(NONEXISTENT_INPUT_FILEPATH)
        );
    }

    @Test
    void readLines_fileIsNull_notOk() {
        assertThrows(
                InvalidFilePathException.class,
                () -> fileService.readLines(null)
        );
    }

    @Test
    void writeDataToFile_validCase_ok() throws IOException {
        fileService.writeDataToFile(OUTPUT_FILEPATH, EXAMPLE_DATA);
        String actual = Files.readString(Path.of(OUTPUT_FILEPATH));
        assertEquals(EXAMPLE_DATA, actual);
    }

    @Test
    void writeDataToFile_fileIsNull_notOk() {
        assertThrows(
                InvalidFilePathException.class,
                () -> fileService.writeDataToFile(null, EXAMPLE_DATA)
        );
    }

    @Test
    void writeDataToFile_dataIsNull_notOk() {
        assertThrows(
                IllegalArgumentException.class,
                () -> fileService.writeDataToFile(OUTPUT_FILEPATH, null)
        );
    }
}
