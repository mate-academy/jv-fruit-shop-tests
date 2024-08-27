package file.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String VALID_PATH = "reportToReadTest.csv";
    private static final String INVALID_PATH = "invalidTest.csv";
    private static final String NO_CSV_FILE = "invalidTest";
    private static final List<String> VALID_LIST = new ArrayList<>(List.of(
            "fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,5",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50"
    ));
    private static FileReader fileReader;

    @BeforeAll
    static void createFileReaderImpl() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_valid_ok() {
        List<String> actual = fileReader.readFromCsv(VALID_PATH);
        assertEquals(actual, VALID_LIST);
    }

    @Test
    void read_invalidFileName_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.readFromCsv(INVALID_PATH));
    }

    @Test
    void read_noCsvFileName_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.readFromCsv(NO_CSV_FILE));
    }

    @Test
    void read_null_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.readFromCsv(null));
    }
}
