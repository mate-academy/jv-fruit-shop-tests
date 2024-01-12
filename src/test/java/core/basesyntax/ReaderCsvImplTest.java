package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.ReaderEmptyFileException;
import core.basesyntax.services.fileprocessing.Reader;
import core.basesyntax.services.fileprocessing.impl.ReaderCsvImpl;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderCsvImplTest {
    private static final String PATH_TO_EMPTY_TEST_REPORT =
            "src/main/resources/readerTestFiles/ReaderCsvImplTestEmptySourceFile.csv";
    private static final String PATH_TO_TEST_REPORT =
            "src/main/resources/readerTestFiles/ReaderCsvImplTestSourceFile.csv";
    private static final List<String> NORMAL_EXPECTED_RESULT =
            List.of("type,fruit,quantity", "b,banana,20", "b,apple,100", "s,banana,100",
                    "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50");
    private static Reader readerCsvIml;

    @BeforeAll
    static void initReader() {
        readerCsvIml = new ReaderCsvImpl();
    }

    @AfterAll
    static void closeReader() {
        readerCsvIml = null;
    }

    /**I think this is a good idea to throw an exception
     * on this stage so that the whole programme
     * doesn't run over an empty file. That's why
     * I'm adding it to the base code*/
    @Test
    void readFile_emptyFile_notOk() {
        assertThrows(ReaderEmptyFileException.class,
                () -> readerCsvIml.readFile(PATH_TO_EMPTY_TEST_REPORT));
    }

    @Test
    void readFile_normalData_Ok() {
        List<String> actualResult = readerCsvIml.readFile(PATH_TO_TEST_REPORT);
        assertEquals(NORMAL_EXPECTED_RESULT, actualResult);
    }
}
