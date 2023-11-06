package core.basesyntax.services.reads;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderImplTest {
    private static CsvFileReader csvFileReader;
    private static final String correctFilePath = "file_for_the_test";
    private static final String incorrectFilePath = "Wrong path to the file";
    private static final String emptyFilePath = "empty_test_file";
    private static List<String> listFromFile;

    @BeforeAll
    static void beforeAll() {
        csvFileReader = new CsvFileReaderImpl();
        listFromFile = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13"
        );
    }

    @Test
    void read_correctFilePath_OK() {
        List<String> expected = csvFileReader.read(correctFilePath);

        assertEquals(listFromFile, expected);
    }

    @Test
    void read_incorrectFilePath_NotOK() {
        assertThrows(IllegalArgumentException.class, () -> {
            csvFileReader.read(incorrectFilePath);
        });
    }

    @Test
    void read_emptyFilePath_NotOK() {
        assertThrows(IllegalArgumentException.class, () -> {
            csvFileReader.read(emptyFilePath);
        });
    }

    @Test
    void read_nullFilePath_NotOK() {
        assertThrows(IllegalArgumentException.class, () -> {
            csvFileReader.read(null);
        });
    }
}
