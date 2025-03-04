package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static String EXPORT_FILE_NAME;
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        EXPORT_FILE_NAME = "src/test/resources/finalReport.csv";
        fileWriter = new FileWriterImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        fileWriter = new FileWriterImpl();
        Files.deleteIfExists(Path.of(EXPORT_FILE_NAME));
    }

    @Test
    void write_validData_Ok() throws IOException {
        String validReport = "fruit,quantity" + LINE_SEPARATOR
                + "banana,152" + LINE_SEPARATOR
                + "apple,90";
        fileWriter.write(validReport, EXPORT_FILE_NAME);
        String actualContent = Files.readString(Path.of(EXPORT_FILE_NAME));
        assertEquals(validReport, actualContent);
    }

    @Test
    void write_emptyContent_Ok() throws IOException {
        String expectedContent = "";
        fileWriter.write(expectedContent, EXPORT_FILE_NAME);
        String actualContent = Files.readString(Path.of(EXPORT_FILE_NAME));
        assertEquals(expectedContent, actualContent);
    }

    @Test
    void write_invalidFilePath_notOk() {
        String content = "Some content";
        String invalidFilePath = "src/test/resources/invalid_folder/test.txt";
        assertThrows(RuntimeException.class, () -> fileWriter.write(content, invalidFilePath));
    }
}
