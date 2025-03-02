package core.basesyntax.servises;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.file.reader.Reader;
import core.basesyntax.file.reader.ReaderImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    private static final String TO_READ_CSV = "src/test/resources/reportToRead.csv";
    private static final String EMPTY_CSV = "src/test/resources/emptyFile.csv";
    private static final String INVALID_CSV = "src/test/resources/invalidFile.csv";
    private Reader reader;

    @BeforeEach
    void setUp() {
        reader = new ReaderImpl();
    }

    @Test
    void read_fileReadValid_Ok() {
        List<String> expected = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        List<String> actual = reader.read(TO_READ_CSV);
        assertEquals(expected, actual);
    }

    @Test
    void read_emptyFile_Ok() {
        assertEquals(Collections.emptyList(), reader.read(EMPTY_CSV));
    }

    @Test
    void read_nullFilePath_NotOk() {
        assertThrows(NullPointerException.class, () -> reader.read(null));
    }

    @Test
    void read_invalidFile_Ok() {
        assertDoesNotThrow(() -> reader.read(INVALID_CSV));
    }
}
