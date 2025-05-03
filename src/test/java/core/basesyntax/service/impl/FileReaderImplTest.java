package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String FILE_PATH
            = "src/main/java/resources/reportToRead.csv";
    private static final String INVALID_FILE_PATH
            = "invalid/reportToRead.csv";
    private FileReader fileReader;
    private List<String> expected;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
        expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
    }

    @Test
    void readFromFile_correctFile_ok() {
        List<String> actual = fileReader.readFromFile(FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    void readFromFile_invalidFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.readFromFile(INVALID_FILE_PATH));
    }
}
