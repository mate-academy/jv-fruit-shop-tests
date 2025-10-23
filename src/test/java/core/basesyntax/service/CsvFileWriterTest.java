package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.CsvFileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class CsvFileWriterTest {
    private CsvFileWriter csvFileWriter;

    @TempDir
   private Path tempDir;

    @BeforeEach
    void setUp() {
        csvFileWriter = new CsvFileWriter();
    }

    @Test
    void createReportFile_validData_success() throws IOException {
        Map<String, Long> data = new LinkedHashMap<>();
        data.put("banana", 40L);
        data.put("apple", 25L);
        Path file = tempDir.resolve("report.csv");

        csvFileWriter.createReportFile(data, file.toString());

        List<String> lines = Files.readAllLines(file);
        assertEquals(List.of("fruit,quantity", "banana,40", "apple,25"), lines);
    }
}
