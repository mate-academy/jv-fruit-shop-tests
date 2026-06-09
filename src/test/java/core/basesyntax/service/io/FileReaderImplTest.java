package core.basesyntax.service.io;

import static core.basesyntax.Main.INPUT_DATA_FILE_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty.csv";

    @Test
    public void nullValue_NotOk() {
        FileReader fileReader = new FileReaderImpl(null);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> fileReader.read());
        assertEquals("input data can not be null", exception.getMessage());
    }

    @Test
    public void emptyFile_NotOk() {
        FileReader fileReader = new FileReaderImpl(EMPTY_FILE_PATH);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> fileReader.read());
        assertEquals("input file can not be empty", exception.getMessage());
    }

    @Test
    public void validWork_Ok() {
        FileReader fileReader = new FileReaderImpl(INPUT_DATA_FILE_PATH);
        List<String> lines = fileReader.read();
        assertTrue(lines.size() > 0);
    }
}
