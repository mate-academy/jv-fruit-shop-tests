package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {

    @TempDir
    private Path tempDir;

    @Test
    void write_validPath_createsFileWithContent() throws IOException {
        FileWriter writer = new FileWriterImpl();
        Path file = tempDir.resolve("report.csv");
        List<String> report = List.of(
                "type, fruit, quantity",
                "b, banana, 30",
                "s, mango, 40"
        );
        writer.write(report, file.toString());
        assertTrue(Files.exists(file));

        List<String> actual = Files.readAllLines(file);
    }

    @Test
    void write_toNonExistingDirectory_trowsExceptions() {
        FileWriter writer = new FileWriterImpl();
        Path badFile = tempDir.resolve("no_such_dir").resolve("report_csv");
        List<String> report = List.of("a", "b");

        assertThrows(RuntimeException.class, () -> writer.write(report, badFile.toString()));
    }
}
