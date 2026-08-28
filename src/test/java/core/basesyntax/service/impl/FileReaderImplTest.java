package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validFile_ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,banana,100"
        );

        List<String> actual =
                fileReader.read("src/test/resources/testReport.csv");

        assertEquals(expected, actual);
    }

    @Test
    void read_invalidPath_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> fileReader.read("src/test/resources/missingFile.csv")
        );
    }

    @Test
    void read_nullPath_notOk() {
        assertThrows(
                 RuntimeException.class,
                 () -> fileReader.read(null)
        );
    }
}
