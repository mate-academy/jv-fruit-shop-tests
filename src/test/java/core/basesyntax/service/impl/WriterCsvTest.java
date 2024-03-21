package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class WriterCsvTest {

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        Storage.DATA.put("apple", 150);
        Storage.DATA.put("banana", 100);
    }

    @AfterEach
    void tearDown() {
        Storage.DATA.clear();
    }

    @Test
    void writeData_ok() throws IOException {
        Path csvPath = tempDir.resolve("output.csv");
        WriterCsv writer = new WriterCsv();
        writer.writeData(csvPath.toString());

        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath.toFile()))) {
            lines = reader.lines().toList();
        }

        assertAll("File content should match expected",
                () -> assertEquals("fruit,quantity", lines.get(0)),
                () -> assertTrue(lines.containsAll(List.of("apple,150", "banana,100")))
        );
    }
}
