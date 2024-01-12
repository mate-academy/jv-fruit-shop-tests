package core.basesyntax.fileprocessing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.ReaderEmptyFileException;
import core.basesyntax.services.fileprocessing.Reader;
import core.basesyntax.services.fileprocessing.impl.ReaderCsvImpl;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderCsvImplTest {
    private static final String PATH_TO_NON_EXISTING_TEST_REPORT =
            "src/main/resources/readerTestFiles/IDoNotExist.csv";
    private static final String EXPECTED_MESSAGE_WHEN_FILE_NON_EXISTING =
            "Can't read from file " + PATH_TO_NON_EXISTING_TEST_REPORT;
    private static final String PATH_TO_EMPTY_TEST_REPORT =
            "src/main/resources/readerTestFiles/ReaderCsvImplTestEmptySourceFile.csv";
    private static final String PATH_TO_TEST_REPORT =
            "src/main/resources/readerTestFiles/ReaderCsvImplTestSourceFile.csv";
    private static final List<String> EXPECTED_RESULT =
            List.of("type,fruit,quantity", "b,banana,20", "b,apple,100", "s,banana,100",
                    "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50");
    private static final List<String> UNEXPECTED_RESULT_SWAPPED_NUMBERS_BY_TWO_LINES =
            List.of("type,fruit,quantity", "b,banana,100", "b,apple,20", "s,banana,13",
                    "p,banana,100", "r,apple,20", "p,apple,10", "p,banana,50", "s,banana,5");
    private static Reader readerCsvImpl;

    @BeforeAll
    static void initReader() {
        readerCsvImpl = new ReaderCsvImpl();
    }

    @AfterAll
    static void closeReader() {
        readerCsvImpl = null;
    }

    /**
     * I think this is a good idea to throw an exception
     * on this stage so that the whole programme
     * doesn't run over an empty file. That's why
     * I'm adding it to the base code
     */
    @Test
    void readFile_fileNotExist_notOk() {
        RuntimeException iOException = assertThrows(RuntimeException.class,
                () -> readerCsvImpl.readFile(PATH_TO_NON_EXISTING_TEST_REPORT));
        assertEquals(iOException.getMessage(), EXPECTED_MESSAGE_WHEN_FILE_NON_EXISTING);
    }

    @Test
    void readFile_emptyFile_notOk() {
        assertThrows(ReaderEmptyFileException.class,
                () -> readerCsvImpl.readFile(PATH_TO_EMPTY_TEST_REPORT));
    }

    @Test
    void readFile_badData_notOk() {
        assertNotEquals(UNEXPECTED_RESULT_SWAPPED_NUMBERS_BY_TWO_LINES,
                readerCsvImpl.readFile(PATH_TO_TEST_REPORT));
    }

    @Test
    void readFile_normalData_Ok() {
        List<String> actualResult = readerCsvImpl.readFile(PATH_TO_TEST_REPORT);
        assertEquals(EXPECTED_RESULT, actualResult);
    }
}
