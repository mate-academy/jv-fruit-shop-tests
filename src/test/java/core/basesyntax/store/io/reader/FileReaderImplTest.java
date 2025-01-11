package core.basesyntax.store.io.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {

    private static final String VALID_FILE = "test-file.csv";
    private static final String INVALID_FILE = "non-existent-file.csv";
    private FileReader fileReader;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = new FileReaderImpl();
        // Створення тестового файлу з вмістом
        Files.writeString(Path.of(VALID_FILE), "type,fruit,quantity\nb,banana,20\np,apple,10");
    }

    @Test
    void read_validFile_ok() {
        List<String> result = fileReader.read(VALID_FILE);
        assertEquals(3, result.size(), "Розмір списку має бути 3 для файлу з трьома рядками");
        assertEquals("type,fruit,quantity", result.get(0),
                "Перший рядок має відповідати очікуваному заголовку");
    }

    @Test
    void read_invalidFile_emptyList() {
        List<String> result = fileReader.read(INVALID_FILE);
        assertTrue(result.isEmpty(), "Список має бути порожнім для неіснуючого файлу");
    }
}
