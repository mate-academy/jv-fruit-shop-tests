package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String VALID_FILE
            = "src/test/java/core/basesyntax/resources/valid_file.csv";
    private static final String INVALID_PATH = "$@1~h%HKJ9|U*28p";
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void readFromFile_validData_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "    b,banana,20",
                "    b,apple,100",
                "    s,banana,100",
                "    p,banana,13",
                "    r,apple,10",
                "    p,apple,20",
                "    p,banana,5",
                "    s,banana,50");
        List<String> actual = fileReader.readFromFile(VALID_FILE);
        assertEquals(expected, actual);
    }

    @Test
    void readFromFile_invalidPath_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.readFromFile(INVALID_PATH));
    }

    @Test
    void readFromFile_nullPath_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.readFromFile(null));
    }
}
