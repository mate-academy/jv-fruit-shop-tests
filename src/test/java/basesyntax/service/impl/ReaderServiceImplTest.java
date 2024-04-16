package basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import basesyntax.exceptions.CustomRuntimeException;
import basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String FILE_TO_READ_PATH = "src/test/resources/file.csv";
    private static final ReaderService readerService = new ReaderServiceImpl();

    @Test
    void readFromFile_pathNull_notOk() {
        assertThrows(CustomRuntimeException.class, () -> {
            readerService.readFromFile(null);
        });
    }

    @Test
    void readFromFile_Ok() {
        List<String> strings = readerService.readFromFile(FILE_TO_READ_PATH);
        String expectedFirstString = "type,fruit,quantity";
        String actualFirstString = strings.get(0);
        assertEquals(actualFirstString, expectedFirstString);
    }
}
