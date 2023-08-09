package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderServiceImp;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderServiceTest {

    private static final FileReaderService READER = new FileReaderServiceImp();
    private static final String FILE_PATH = "src/test/resources/data.csv";

    @Test
    void read_correctPath_ok() {
        List<String> actualData = READER.read(FILE_PATH);
        List<String> exceptedData = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        assertEquals(exceptedData, actualData);
    }

    @Test
    void read_incorrectPath_notOK() {
        String incorrectPath = "incorrectPath";
        assertThrows(RuntimeException.class, () -> READER.read(incorrectPath));
    }

    @Test
    void read_pathIsNull_notOk() {
        String nullPath = null;
        assertThrows(RuntimeException.class, () -> READER.read(nullPath));
    }
}
