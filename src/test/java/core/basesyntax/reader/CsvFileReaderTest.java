package core.basesyntax.reader;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FileWasNotReadException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    public static final int ZERO_INDEX_POSITION = 0;
    public static final String TYPE_B = "b";
    public static final String FRUIT_TYPE_BANANA = "banana";
    public static final String QUANTITY_EXAMPLE = "20";
    private static final String FILE_TO_READ = "src/test/resources/db/ReadTransactionTest";
    private static final String EMPTY_FILE = "src/test/resources/db/empty.csv";
    private static final String NOT_EXISTENT_FILE = "";

    private CsvFileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new CsvFileReader(FILE_TO_READ);
    }

    @Test
    void reading_notNullFile_Ok() {
        List<List<String>> actualLists = fileReader.readTransactions();

        Assertions.assertNotNull(actualLists);
    }

    @Test
    void reading_notEmptyFile_Ok() {
        CsvFileReader fileReader = new CsvFileReader(FILE_TO_READ);
        List<List<String>> actualLists = fileReader.readTransactions();

        List<List<String>> expectedLists = new ArrayList<>();
        expectedLists.add(List.of(
                TYPE_B,
                FRUIT_TYPE_BANANA,
                QUANTITY_EXAMPLE)
        );

        Assertions.assertNotNull(actualLists);
        Assertions.assertEquals(
                actualLists.get(ZERO_INDEX_POSITION),
                expectedLists.get(ZERO_INDEX_POSITION)
        );
    }

    @Test
    void reading_fileDoesntExist_notOk() {
        CsvFileReader fileReader = new CsvFileReader(NOT_EXISTENT_FILE);
        assertThrows(FileWasNotReadException.class, fileReader::readTransactions);
    }

    @Test
    void reading_emptyFile_notOk() {
        CsvFileReader fileReader = new CsvFileReader(EMPTY_FILE);
        List<List<String>> actualList = fileReader.readTransactions();
        Assertions.assertTrue(actualList.isEmpty());
    }

    @Test
    void fileReader_read_nullPath_notOk() {
        CsvFileReader fileReader = new CsvFileReader(null);
        Assertions.assertThrows(RuntimeException.class, fileReader::readTransactions);
    }
}
