package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.operations.report.FileWriter;
import core.basesyntax.service.operations.report.impl.FileWriterImpl;
import exception.CustomException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_FILE_PATH = "src/test/resources/test_report.txt";
    private static final String TEST_CONTENT = "This is a test report content.";
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }

    @Test
    void writeDataToFile_ValidFilePath_ok() throws IOException {
        fileWriter.writeDataToFile(TEST_CONTENT, TEST_FILE_PATH);
        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());
        List<String> actualLines = Files.readAllLines(Path.of(TEST_FILE_PATH));
        assertEquals(1, actualLines.size());
        assertEquals(TEST_CONTENT, actualLines.get(0));
    }

    @Test
    void writeDataToFile_NullContent_notOk() {
        CustomException customException = assertThrows(CustomException.class, () ->
                fileWriter.writeDataToFile(null, TEST_FILE_PATH));
        assertEquals("Report content or file path is null", customException.getMessage());
    }
}
