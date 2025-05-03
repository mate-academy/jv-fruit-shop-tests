package core.basesyntax.service.writer;

import static core.basesyntax.TestConstants.HEADER;
import static core.basesyntax.TestConstants.INVALID_PATH;
import static core.basesyntax.TestConstants.TYPE_FRUIT_QUANTITY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CsvWriterImplTest {
    private CsvWriter csvWriter;

    @BeforeEach
    void setUp() {
        csvWriter = new CsvWriterImpl();
    }

    @Test
    void writeToFile_validData_shouldNotThrowException(@TempDir Path tempDir) {
        Path filePath = tempDir.resolve("test.csv");
        List<String> info = new ArrayList<>();
        info.add(HEADER);
        info.add(TYPE_FRUIT_QUANTITY);
        assertDoesNotThrow(() -> csvWriter.writeToFile(filePath, info));
    }

    @Test
    void writeToFile_invalidFilePath_shouldThrowException() {
        Path filePath = Path.of(INVALID_PATH);
        List<String> info = new ArrayList<>();
        info.add(HEADER);
        info.add(TYPE_FRUIT_QUANTITY);
        assertThrows(RuntimeException.class, () -> csvWriter.writeToFile(filePath, info));
    }
}
