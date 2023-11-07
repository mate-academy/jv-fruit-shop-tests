package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static final String READ_VALID_FILE_LOCATION = "src/test/java/core/basesyntax/"
            + "filesForTesting/fileToRead.csv";
    private static final String READ_INVALID_FILE_LOCATION = "some random location";
    private static final String LIST_ELEMENT1 = "type,fruit,quantity";
    private static final String LIST_ELEMENT2 = "b,banana,20";
    private static final String LIST_ELEMENT3 = "b,apple,100";
    private static List<String> lines = new ArrayList<>();
    private static DataReader csvFileReader;

    @BeforeAll
    static void beforeAll() {
        lines.add(LIST_ELEMENT1);
        lines.add(LIST_ELEMENT2);
        lines.add(LIST_ELEMENT3);
        csvFileReader = new CsvFileReader();
    }

    @Test
    void readValidPathFile_Ok() {
        List<String> result = csvFileReader.readFile(READ_VALID_FILE_LOCATION);
        assertEquals(result, lines);
    }

    @Test
    void readInvalidPathFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> csvFileReader.readFile(READ_INVALID_FILE_LOCATION));
    }
}
