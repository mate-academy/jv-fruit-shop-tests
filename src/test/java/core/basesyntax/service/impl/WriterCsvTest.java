package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class WriterCsvTest {
    private static final String FILE_TO_WRITE_INTO = "output.csv";

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        Storage.DATA.clear();
        Storage.DATA.put("apple", 150);
        Storage.DATA.put("banana", 100);
    }

    @Test
    void writeData_ok() {
        Path csvPath = tempDir.resolve(FILE_TO_WRITE_INTO);
        WriterCsv writer = new WriterCsv();
        writer.writeData(csvPath.toString());
        List<String> lines = TestFileUtils.readLinesFromFile(csvPath.toString());
        assertAll("File content should match expected",
                () -> assertTrue(lines.containsAll(List.of("apple,150", "banana,100")))
        );
    }
}
