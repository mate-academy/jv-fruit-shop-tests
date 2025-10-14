package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

import core.basesyntax.exception.FruitShopException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterTest {
    @TempDir
    private Path tempDir;

    private FileWriter writer;

    @BeforeEach
    void setUp() {
        writer = new FileWriterImpl();
    }

    @Test
    void writeShouldThrow_whenDataOrPathIsNull() {
        assertThrows(FruitShopException.class, () -> writer.write(null, null));
    }

    @Test
    void writeShouldCreateFileWithContent_whenDataAndPathProvided() throws Exception {
        Path file = tempDir.resolve("output.csv");
        String data = String.join(System.lineSeparator(),
                "type,fruit,quantity",
                "b,banana,2"
        ) + System.lineSeparator();

        writer.write(data, file.toString());

        List<String> actual = Files.readAllLines(file, StandardCharsets.UTF_8);
        List<String> expected = List.of("type,fruit,quantity", "b,banana,2");
        assertLinesMatch(expected, actual);
    }
}
