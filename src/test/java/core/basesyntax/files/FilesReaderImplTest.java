package core.basesyntax.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FilesReaderImplTest {
    private static final String CORRECT_INPUT_PATH = "src/test/resources/correctInput.csv";
    private static final String EMPTY_INPUT_PATH = "src/test/resources/emptyFile.csv";
    private static final String NONEXISTENT_PATH = "nonexistent.csv";
    private static FilesReader reader;

    @BeforeAll
    static void setUp() {
        reader = new FilesReaderImpl();
    }

    @Test
    void read_CorrectInput_ok() {
        List<String> lines = reader.read(CORRECT_INPUT_PATH);

        assertEquals(9, lines.size());
        assertEquals("b,banana,20", lines.get(1));
    }

    @Test
    void read_EmptyInput_Ok() {
        List<String> lines = reader.read(EMPTY_INPUT_PATH);

        assertEquals(0, lines.size());
    }

    @Test
    void read_NonexistentFile_notOk() {
        assertThrows(RuntimeException.class, () -> reader.read(NONEXISTENT_PATH));
    }
}
