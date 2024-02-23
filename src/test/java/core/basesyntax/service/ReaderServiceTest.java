package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String TEST_FILE_PATH = "src/test/resources/test.cvs";
    private static final String FAIL_FILE_PATH = "test.cvs";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_wrongFilePath_ok() {
        List<String> actual = readerService.readFromFile(FAIL_FILE_PATH);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void readFromFile_filePath_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,apple,20");
        List<String> actual = readerService.readFromFile(TEST_FILE_PATH);
        Assertions.assertEquals(expected.size(), actual.size());
        Assertions.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void readFromFile_filePathNull_ok() {
        Assertions.assertTrue(readerService.readFromFile(null).isEmpty());
    }

    @Test
    void readFromFile_filePathEmpty_ok() {
        Assertions.assertTrue(readerService.readFromFile("").isEmpty());
    }
}
