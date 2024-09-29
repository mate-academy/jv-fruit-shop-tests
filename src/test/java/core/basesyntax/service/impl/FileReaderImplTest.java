package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReader reader = new FileReaderImpl();

    @Test
    void read_nullFile_notOk() {
        String nullName = null;
        Assertions.assertThrows(RuntimeException.class, () -> reader.read(nullName));
    }

    @Test
    void read_notExistingFile_notOk() {
        String fileName = "notExistingFile.csv";
        Assertions.assertThrows(RuntimeException.class, () -> reader.read(fileName));
    }

    @Test
    void read_correctFile_Ok() {
        List<String> expectedList = List.of("    b,banana,20",
                 "    b,apple,100",
                 "    s,banana,100",
                 "    p,banana,13",
                 "    r,apple,10",
                 "    p,apple,20",
                 "    p,banana,5",
                 "    s,banana,50");
        List<String> actualList = reader.read("input.csv");
        Assertions.assertEquals(expectedList, actualList);
    }
}
