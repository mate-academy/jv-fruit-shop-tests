package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_dataNull_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileWriter.write(null, "reportToRead.csv");
        });
    }

    @Test
    void write_dataPathNull_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileWriter.write("finalReport.csv", null);
        });
    }

    @Test
    void write_dataPathBlank_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileWriter.write("finalReport.csv", " ");
        });
    }

    @Test
    void write_validData_Ok() {
        String fileName = "reportToRead.csv";
        String data = "finalReport.csv";
        assertDoesNotThrow(() -> {
            fileWriter.write(data, fileName);
        });
    }
}
