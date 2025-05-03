package core.basesyntax.dao.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WriterCsvTest {
    private final WriterCsv writer = new WriterCsvImpl();
    private final Path filePath = Path.of("src/test/resources/report.csv");
    private final List<String> lines = List.of(
            "fruit,quantity",
            "banana,152",
            "apple,90");

    @Test
    void write_Ok() {
        writer.writeToFile(filePath, lines);
        Assertions.assertTrue(Files.exists(filePath));
        try {
            List<String> actual = Files.readAllLines(filePath);
            Assertions.assertEquals(lines, actual);
        } catch (IOException e) {
            Assertions.fail("IOException occurred", e);
        }
    }

    @Test
    void write_wrongPath_NotOk() {
        Path wrongPath = Path.of("gigi/gaga/resources/report.csv");
        Assertions.assertThrows(RuntimeException.class, () -> writer.writeToFile(wrongPath, lines));
    }

    @Test
    void write_Null_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> writer.writeToFile(null, lines));
    }
}
