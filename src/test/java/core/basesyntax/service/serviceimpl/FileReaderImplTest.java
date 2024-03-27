package core.basesyntax.service.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exception.NoFileToReadException;
import core.basesyntax.service.interfaces.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String VALID_DATA_FILE_PATH = "src/test/resources/fruitts.csv";
    private static final String EMPTY_FILE = "src/test/resources/service/emptyFile.csv";
    private static final String FILE_ABSENT = "src/test/resources/service/absentFile.csv";

    private FileReader fileReader = new FileReaderImpl();
    private List<String> lines;

    @Test
    void read_fileValidData_OK() {
        lines = fileReader.readFile(VALID_DATA_FILE_PATH);
        assertFalse(lines.isEmpty());
    }

    @Test
    void read_fileIsAbsent_noTOk() {
        assertThrows(NoFileToReadException.class, () -> {
            fileReader.readFile(FILE_ABSENT);
        });
    }

    @Test
    void read_fileIsEmpty_notOk() throws IOException {
        lines = Files.readAllLines(Path.of(EMPTY_FILE));
        assertTrue(lines.isEmpty());
    }
}
