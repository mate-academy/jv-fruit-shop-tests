package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReaderCsvTest {
    private static final String FILE_TO_READ = "test.csv";

    @TempDir
    private Path tempDir;

    @Test
    void readData_ok() throws IOException {
        Path csvPath = tempDir.resolve(FILE_TO_READ);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath.toFile()))) {
            bw.write("Operation,Product,Amount" + System.lineSeparator());
            bw.write("b,apple,10" + System.lineSeparator());
            bw.write("s,banana,20");
        }

        List<String> lines = readLinesFromFile(csvPath);

        assertAll("Should read all lines except title",
                () -> assertNotNull(lines),
                () -> assertEquals(2, lines.size()),
                () -> assertEquals("b,apple,10", lines.get(0)),
                () -> assertEquals("s,banana,20", lines.get(1))
        );
    }

    @Test
    void readData_notOk() {
        ReaderCsv reader = new ReaderCsv();
        String invalidPath = "non_existent_file.csv";
        Exception exception = assertThrows(RuntimeException.class,
                () -> reader.readData(invalidPath));

        assertTrue(exception.getMessage().contains("Can't read from file"));
    }

    public static List<String> readLinesFromFile(Path filePath) {
        ReaderCsv reader = new ReaderCsv();
        return reader.readData(filePath.toString());
    }
}
