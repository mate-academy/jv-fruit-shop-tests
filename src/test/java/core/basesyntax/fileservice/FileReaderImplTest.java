package core.basesyntax.fileservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String EMPTY_FILE = "src/test/resources/file2.csv";
    private static final String FILE_NOT_EXIST = "src/test/resources/file9.csv";
    private FileReader fileReader = new FileReaderImpl();

    @Test
    void transfer_FileIsEmpty_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.readFile(EMPTY_FILE);
        });
    }

    @Test
    void transfer_FileNotExists_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.readFile(FILE_NOT_EXIST);
        });
    }

    @Test
    void transfer_NameOfFileIsNull_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.readFile(null);
        });
    }
}
