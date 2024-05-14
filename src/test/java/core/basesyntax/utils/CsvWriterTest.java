package core.basesyntax.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvWriterTest {
    @Test
    void writeReport_validFile_ok() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        String report = "fruit,quantity\napple,50\nbanana,30\n";
        CsvWriter.writeReport(tempFile.toString(), report);
        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(3, lines.size());
        assertEquals("fruit,quantity", lines.get(0));
        assertEquals("apple,50", lines.get(1));
        assertEquals("banana,30", lines.get(2));
        Files.delete(tempFile);
    }

    @Test
    void writeReport_fileNotFound_notOk() {
        assertThrows(RuntimeException.class,
                () -> CsvWriter.writeReport("/invalid_path/report.csv", "report"));
    }
}
