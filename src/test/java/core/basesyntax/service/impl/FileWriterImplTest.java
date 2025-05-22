package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private FileWriter fileWriter;
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void writeToFile_reportIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileWriter.writeToFile(null,"correctPath.csv");
        });
    }

    @Test
    void writeToFile_wrongPath_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileWriter.writeToFile("Hello, world!","C:/invalid_path/file.csv");
        });
    }

    @Test
    void writeToFile_correctDate_Ok() {
        assertDoesNotThrow(() -> fileWriter
                .writeToFile(reportGenerator.getReport(),"correctPath.csv"));
    }
}
