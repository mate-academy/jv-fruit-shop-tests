package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class WriterImplTest {
    private final Writer writer = new WriterImpl();
    private final String pathString = "src/test/resources/testReport.csv";
    private final String report = "test123";

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(Path.of(pathString));
        } catch (IOException e) {
            System.out.println("Could not delete file: " + e.getMessage());
        }
    }

    @Test
    void writeToFile_fileWritten_ok() {
        writer.writeToFile(pathString, report);
        Path path = Path.of(pathString);
        assertTrue(Files.exists(path), "File was not written");
        List<String> reportLines;
        try {
            reportLines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + pathString, e);
        }
        assertEquals(report, reportLines.get(reportLines.size() - 1));
        assertEquals(1, reportLines.size(), "Report should contain exactly one line");
    }

    @Test
    void writeToFile_pathIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> writer.writeToFile(null,
                report), "Runtime exception expected");
    }

    @Test
    void writeToFile_pathDoesNotExist_notOk() {
        String wrongPath = "src/test/resourceses/testReport.csv";
        assertThrows(RuntimeException.class, () -> writer.writeToFile(wrongPath,
                report), "Runtime exception expected");
    }

    @Test
    void writeToFile_dataIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> writer.writeToFile(pathString,
                null), "Runtime exception expected");
    }
}
