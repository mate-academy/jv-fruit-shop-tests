package core.basesyntax.file.reader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
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
    void fileReadValid_Ok() {
        assertDoesNotThrow(() -> reader.read(TO_READ_CSV));
    }

    @Test
    void emptyFile_Ok() {
        assertEquals(Collections.emptyList(), reader.read(EMPTY_CSV));
    }

    @Test
    void nullFilePath_NotOk() {
        assertThrows(NullPointerException.class, () -> reader.read(null));
    }

    @Test
    void invalidFile_Ok() {
        assertDoesNotThrow(() -> reader.read(INVALID_CSV));
    }
}
