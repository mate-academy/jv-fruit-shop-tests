package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FileReader;
import core.basesyntax.dao.impl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private static final String NONEXISTENT_FILE = "nonexistentfolder/nonexistentfile.csv";
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validFile_Ok() {
        List<String> expected = List.of("BALANCE,apple,100", "SUPPLY,banana,50");
        List<String> actual = fileReader.read("src/test/testfile.csv");
        assertEquals(expected, actual);
    }

    @Test
    void read_nonExistentFile_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReader
                .read(NONEXISTENT_FILE));
    }
}
