package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new CsvFileWriter();
    }

    @Test
    void write_nullInputData_NotOk() {
        String content = null;
        assertThrows(RuntimeException.class, () -> fileWriter.write(content, "report.csv"),
                "Can't write to file");
    }

    @Test
    void write_nullFileName_NotOk() {
        String content = "valid";
        String fileName = null;
        assertThrows(RuntimeException.class, () -> fileWriter.write(content, fileName),
                "Can't write to file");
    }

    @Test
    void write_validData_Ok() {
        String content = "valid";
        String fileName = "report.csv";
        assertTrue(fileWriter.write(content, fileName));
    }
}
