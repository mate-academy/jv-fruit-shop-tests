package core.basesyntax.serviceimpl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String PATH_TO_READ = "src/test/resources/testFile.csv";
    private static final String WRONG_FILE = "src/test/resources/wrongFile.csv";
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void read_Ok() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("b,apple,100");
        expectedList.add("s,banana,100");
        expectedList.add("p,banana,13");
        List<String> actualList = readerService.read(PATH_TO_READ);
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void read_InvalidFile_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> readerService.read(WRONG_FILE));
    }

    @AfterEach
    void tearDown() {
        Storage.DB.clear();
    }
}
