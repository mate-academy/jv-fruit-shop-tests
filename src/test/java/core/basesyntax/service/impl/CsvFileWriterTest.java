package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static final String VALID_FILE_TO_WRITE = "src/main/resources/output.csv";
    private static final String WRONG_FILE_TO_WRITE = "2/2/2/output.csv";
    private static final String EXPECTED_REPORT = """
            fruit,quantity
            banana,109
            apple,109""";
    private static final List<String> EXPECTED_READER_RESULT_AFTER_WRITE = List.of(
            "fruit,quantity", "banana,109", "apple,109");
    private FileWriterService reportFileWriter;

    @BeforeEach
    void setUp() {
        reportFileWriter = new CsvFileWriter();
    }

    @Test
    void fileWriter_ValidPath_Ok() {
        reportFileWriter.write(EXPECTED_REPORT, VALID_FILE_TO_WRITE);
        List<String> actual = getStringList();
        assertEquals(EXPECTED_READER_RESULT_AFTER_WRITE, actual);
    }

    @Test
    void fileWriter_WrongPath_ThrowException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reportFileWriter.write(EXPECTED_REPORT, WRONG_FILE_TO_WRITE));
        assertEquals("Can't write data to file: " + WRONG_FILE_TO_WRITE, exception.getMessage());
    }

    private List<String> getStringList() {
        List<String> lineList = null;
        try {
            lineList = Files.readAllLines(Paths.get(VALID_FILE_TO_WRITE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + VALID_FILE_TO_WRITE, e);
        }
        return lineList;
    }
}
