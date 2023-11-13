package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileWriterTest {
    private static final String OUTPUT_FILE_PATH = "src/test/resources/output_file.csv";
    private static final String INVALID_FILE_PATH = "non_existent_folder/output_file.csv";
    private static final String NEW_LINE = System.lineSeparator();
    private FileWriterService csvFileWriter;

    @BeforeEach
    void setUp() {
        csvFileWriter = new CsvFileWriter();
    }

    @Test
    void write_validContentAndFilePath_ok() {
        String content = "b,banana,20" + NEW_LINE + "s,banana,100" + NEW_LINE + "p,banana,5";
        String filePath = OUTPUT_FILE_PATH;

        assertDoesNotThrow(() -> csvFileWriter.write(content, filePath));
    }

    @Test
    void write_nullContent_notOk() {
        String content = null;
        String filePath = OUTPUT_FILE_PATH;
        assertThrows(Exception.class, () -> csvFileWriter.write(content, filePath));
    }

    @Test
    void write_emptyContent_notOk() {
        String content = "";
        String filePath = OUTPUT_FILE_PATH;
        assertThrows(Exception.class, () -> csvFileWriter.write(content, filePath));
    }

    @Test
    void write_invalidFilePath_notOk() {
        String content = "b,banana,20" + NEW_LINE + "b,apple,100" + NEW_LINE + "s,banana,100";
        String filePath = INVALID_FILE_PATH;
        assertThrows(Exception.class, () -> csvFileWriter.write(content, filePath));
    }
}
