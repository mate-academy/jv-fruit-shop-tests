package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyFileReaderImplTest {
    private static final String DEFAULT_FILE_NAME = "reportToRead.csv";
    private static final List<String> DEFAULT_READED_STRING_LIST;
    private static final MyFileReaderImpl myFileReader;
    private List<String> expected;

    static {
        DEFAULT_READED_STRING_LIST = new ArrayList<>(List.of(
                "    type,fruit,quantity",
                "    b,banana,20",
                "    b,apple,100",
                "    s,banana,100",
                "    p,banana,13",
                "    r,apple,10",
                "    p,apple,20",
                "    p,banana,5",
                "    s,banana,50"));
        myFileReader = new MyFileReaderImpl();
    }

    @BeforeEach
    void setUp() {
        expected = DEFAULT_READED_STRING_LIST;
    }

    @Test
    void read_validInput_ok() {
        List<String> actual = myFileReader.read(DEFAULT_FILE_NAME);
        assertEquals(expected, actual);
    }

    @Test
    void read_invalidInput_notOk() {
        String expected = "Error reading file: fakeFile.csv";
        RuntimeException actual = assertThrows(RuntimeException.class,
                () -> myFileReader.read("fakeFile.csv"));
        assertEquals(expected, actual.getMessage());
    }
}
