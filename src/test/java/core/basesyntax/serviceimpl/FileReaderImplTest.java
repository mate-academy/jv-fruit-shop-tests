package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/validReportToRead.csv";
    private static final String NON_EXIST_FILE_PATH = "src/test/resources/nonExistFile.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyReportToRead.csv";

    @Test
    void read_ValidFile_Ok() {
        FileReader fileReader = new FileReaderImpl();

        List<String> lines = fileReader.read(VALID_FILE_PATH);

        assertFalse(lines.isEmpty());
    }

    @Test
    void read_NonExistFile_NotOk() {
        FileReader fileReader = new FileReaderImpl();

        assertThrows(RuntimeException.class,
                () -> fileReader.read(NON_EXIST_FILE_PATH));
    }

    @Test
    void read_EmptyFile_NotOk() {
        FileReader fileReader = new FileReaderImpl();

        assertThrows(RuntimeException.class,
                () -> fileReader.read(EMPTY_FILE_PATH));
    }
}
