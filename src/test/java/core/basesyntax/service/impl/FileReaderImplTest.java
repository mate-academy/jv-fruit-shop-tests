package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
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
        String validFile = "src/test/java/core/basesyntax/resources/validfile.csv";
        List<String> actual = fileReader.readFromFile(validFile);
        assertEquals(expected, actual);
    }

    @Test
    void readFromFile_invalidPath_notOk() {
        String invalidPath = "$@1~h%HKJ9|U*28p";
        assertThrows(RuntimeException.class, () -> fileReader.readFromFile(invalidPath));
    }

    @Test
    void readFromFile_nullPath_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.readFromFile(null));
    }
}
