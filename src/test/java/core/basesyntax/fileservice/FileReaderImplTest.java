package core.basesyntax.fileservice;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.List;

class FileReaderImplTest {
    private static final String EMPTY_FILE = "src/test/resources/file2.csv";
    private static final String FILE_NOT_EXIST = "src/test/resources/file9.csv";
    private static final String EXISTING_FILE = "src/test/resources/expected.csv";
    private FileReader fileReader = new FileReaderImpl();

    @Test
    void transfer_FileIsEmpty_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.readFile(EMPTY_FILE);
        });
    }

    @Test
    void transfer_FileNotExists_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.readFile(FILE_NOT_EXIST);
        });
    }

    @Test
    void transfer_NameOfFileIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.readFile(null);
        });
    }

    @Test
    void read_existingFile_ok() {
        List<String> expected = List.of("fruit,quantity", "b,banana,152");
        List<String> actual = fileReader.readFile(EXISTING_FILE);
        assertEquals(expected, actual);
    }
}
