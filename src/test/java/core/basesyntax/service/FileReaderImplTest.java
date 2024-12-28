package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final Path INPUT_FILE = Paths.get("src/test/resources/input.csv");
    private static final Path EMPTY_INPUT_FILE = Paths.get("src/test/resources/empty_input.csv");
    private static final Path ABSENT_FILE = Paths.get("src/test/resour/ces/absent.csv");
    private static FileReader reader;

    @BeforeAll
    static void beforeAll() {
        reader = new FileReaderImpl();
    }

    @Test
    void read_existingInputFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20");
        List<String> actual = reader.read(INPUT_FILE);
        assertEquals(expected, actual);
    }

    @Test
    void read_notExistingInputFile_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            reader.read(ABSENT_FILE);
        });
    }

    @Test
    void read_emptyInputFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = reader.read(EMPTY_INPUT_FILE);
        assertEquals(expected, actual);
    }

    @Test
    void read_nullPathFile_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            reader.read(null);
        });
    }
}
