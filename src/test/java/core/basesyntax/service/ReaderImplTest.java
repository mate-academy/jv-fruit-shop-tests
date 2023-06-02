package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.WrongFileType;
import core.basesyntax.service.impl.ReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderImplTest {
    private static final String EMPTY_FILE_NAME = "src/main/resources/emptyData.csv";
    private static final String NON_EXISTENT_FILE_NAME = "src/main/resources/nonExistent.csv";
    private static final String TWO_LINES_FILE_NAME = "src/main/resources/shortTestData.csv";
    private static final String WRONG_TYPE_FILE_NAME = "src/main/resources/shortTestData.css";
    private static final String VALID_FILE_NAME = "src/main/resources/testData.csv";
    private static Reader reader;

    @BeforeAll
    static void beforeAll() {
        reader = new ReaderImpl();
    }

    @Test
    public void read_twoLinesFileName_ok() {
        List<String> actual = reader.read(TWO_LINES_FILE_NAME);
        List<String> expected = List.of("b,banana,20");
        assertEquals(expected, actual);
    }

    @Test
    public void read_validFileName_ok() {
        List<String> actual = reader.read(VALID_FILE_NAME);
        List<String> expected = List.of("b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50");
        assertEquals(expected, actual);
    }

    @Test
    public void read_nullFileName_notOk() {
        assertThrows(RuntimeException.class, () -> reader.read(null));
    }

    @Test
    public void read_emptyFileName_ok() {
        List<String> actual = reader.read(EMPTY_FILE_NAME);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void read_nonExistentFileName_notOk() {
        assertThrows(RuntimeException.class, () -> reader.read(NON_EXISTENT_FILE_NAME));
    }

    @Test
    public void read_wrongTypeFileName_notOk() {
        assertThrows(WrongFileType.class, () -> reader.read(WRONG_TYPE_FILE_NAME));
    }
}
