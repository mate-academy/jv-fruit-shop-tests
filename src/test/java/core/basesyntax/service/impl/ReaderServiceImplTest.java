package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private final String nonExistFile = "nonExistFile.csv";
    private final ReaderService readerService = new ReaderServiceImpl();

    @Test
    void readFromNonExistFile_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                readerService.readFromFile(nonExistFile));
    }

    @Test
    void readFromExistFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity","b,banana,20",
                "b,apple,100");
        String existFile = "src/test/java/resources/dataForTest.csv";
        List<String> actual = readerService.readFromFile(existFile);
        Assertions.assertEquals(expected, actual);
    }
}
