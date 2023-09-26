package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_throwsExceptionWithIncorrectPath_Ok() {
        String filename = "path to file";
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(filename));
    }

    @Test
    void readFromFile_readAndReturnCorrectDataList_Ok() {
        String inputDataFileName = "src/main/resources/input.csv";
        List<String> expected = List.of("type,fruit,quantity", "    b,banana,20", "    b,apple,100",
                "    s,banana,100", "    p,banana,13", "    r,apple,10", "    p,apple,20",
                "    p,banana,5", "    s,banana,50");
        List<String> actual = readerService.readFromFile(inputDataFileName);
        assertEquals(expected, actual);
    }
}
