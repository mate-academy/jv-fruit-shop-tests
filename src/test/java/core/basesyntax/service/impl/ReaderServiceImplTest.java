package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String VALID_TEST_FILE_PATH
            = "src/test/resources/validDataTestFile.csv";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_validData_ok() {
        List<String> expected = List.of("b,apple,200", "b,orange,200", "s,apple,100");
        List<String> actual = readerService.readFromFile(VALID_TEST_FILE_PATH);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readFromFile_nullPath_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(null));
    }

    @Test
    void readFromFile_invalidPath_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> readerService.readFromFile("invalidPath"));
    }
}
