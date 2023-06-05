package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String VALID_FILE_PATH = "src/test/java/resources/databaseTest.csv";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_validData_ok() {
        List<String> expect = List.of("b,banana,20", "b,apple,100");
        List<String> actual = readerService.readFromFile(
                VALID_FILE_PATH);
        Assertions.assertEquals(expect, actual);
    }

    @Test
    void readFromFile_fileNotExists_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                readerService.readFromFile(null)
        );
    }
}
