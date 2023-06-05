package core.basesyntax.services.imps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImpTest {
    private static ReaderServiceImp readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImp();
    }

    @Test
    void readFromFile_validFile_ok() {
        String actual = readerService.readFromFile("src/test/resources/source_file_test.csv");
        String expected =
                "type,fruit,quantity/b,banana,20/b,apple,100/s,banana,100/"
                        + "p,banana,13/r,apple,10/p,apple,20/p,banana,5/s,banana,50";
        assertEquals(expected, actual);
    }

    @Test
    void readFromFile_filePathNotFound_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile("src/test/resources/source_test.csv"),
                "File not found! Runtime exception should be thrown");
    }

    @Test
    void readFromFile_emptyFile_notOk() {
        String actual = readerService.readFromFile("src/test/resources/source_file_empty.csv");
        assertTrue(actual.isEmpty());
    }
}
