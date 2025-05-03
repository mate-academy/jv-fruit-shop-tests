package core.basesyntax.dao;

import static core.basesyntax.service.TestConstants.DEFAULT_FILE_NAME_TO_READ;
import static core.basesyntax.service.TestConstants.DEFAULT_READED_STRING_LIST;
import static core.basesyntax.service.TestConstants.INVALID_FILE_NAME_TO_READ;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyFileReaderImplTest {
    private static MyFileReaderImpl myFileReader;
    private List<String> expected;

    @BeforeAll
    static void beforeAll() {
        myFileReader = new MyFileReaderImpl();
    }

    @BeforeEach
    void setUp() {
        expected = DEFAULT_READED_STRING_LIST;
    }

    @Test
    void read_validInput_ok() {
        List<String> actual = myFileReader.read(DEFAULT_FILE_NAME_TO_READ);
        assertEquals(expected, actual);
    }

    @Test
    void read_invalidInput_notOk() {
        String expected = new String("Can't find the file: " + INVALID_FILE_NAME_TO_READ);
        RuntimeException actual = assertThrows(RuntimeException.class,
                () -> myFileReader.read(INVALID_FILE_NAME_TO_READ));
        assertEquals(expected, actual.getMessage());
    }
}
