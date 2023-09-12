package core.basesyntax.file.processing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderImplTest {
    private static final String CORRECT_INPUT_PATH = "src/test/resources/correctInput.csv";
    private static final String EMPTY_INPUT_PATH = "src/test/resources/emptyFile.csv";
    private static final String WHITE_SPACES_PATH = "src/test/resources/whiteSpacesFile.csv";
    private static final String NONEXISTENT_PATH = "nonexistent.csv";
    private static CsvFileReader reader;

    @BeforeAll
    static void setUp() {
        reader = new CsvFileReaderImpl();
    }

    @Test
    void read_CorrectInput_ok() {
        List<String> lines = reader.read(CORRECT_INPUT_PATH);

        assertEquals(8, lines.size());
        assertEquals("b,banana,20", lines.get(0));
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

    @Test
    void read_InvalidFilePath_NotOk() {
        String invalidFilePath = "src/test/java/resources";
        assertThrows(RuntimeException.class, () -> reader.read(invalidFilePath));
    }

    @Test
    void read_FileWithWhitespaceLines_IgnoresWhitespaceLines() {
        List<String> lines = reader.read(WHITE_SPACES_PATH);

        assertEquals(8, lines.size());
        assertEquals("b,banana,20", lines.get(0));
        assertEquals("p,banana,13", lines.get(3));
        assertEquals("s,banana,50", lines.get(7));
    }
}
