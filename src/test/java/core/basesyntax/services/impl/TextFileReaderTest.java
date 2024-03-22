package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FileOperationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextFileReaderTest {
    private static final String TEST_FILE_PATH = "src/test/resources/testFile.csv";
    private static final List<String> EXPECTED_CONTENTS = Arrays.asList(
            "Line 1",
            "Line 2",
            "Line 3");
    private TextFileReader textFileReader;

    @BeforeEach
    void setUp() throws IOException {
        textFileReader = new TextFileReader();
        Files.write(Path.of(TEST_FILE_PATH), EXPECTED_CONTENTS);
    }

    @Test
    void readFileValidFilePathReturnsContentsOk() {
        List<String> actualContents = textFileReader.readFile(TEST_FILE_PATH);
        assertEquals(EXPECTED_CONTENTS, actualContents,
                "Expected contents: " + EXPECTED_CONTENTS + ", Actual contents: " + actualContents);
    }

    @Test
    void readFileInvalidFilePathThrowsExceptionNotOk() {
        assertThrows(FileOperationException.class,
                () -> textFileReader.readFile("nonexistent.txt"),
                "Expected FileOperationException for invalid file path");
    }

    @AfterAll
    static void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }
}

