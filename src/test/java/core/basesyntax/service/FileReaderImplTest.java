package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {

    private static final String VALID_FILE_PATH = "src/test/resources/valid-file.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty-file.csv";
    private static final String INVALID_FILE_PATH = "src/test/resources/invalid-file.csv";

    private final FileReaderImpl fileReader = new FileReaderImpl();

    @Test
    public void readFile_validFile_ok() {
        List<String> data = fileReader.read(VALID_FILE_PATH);
        assertEquals(3, data.size());
    }

    @Test
    public void readFile_emptyFile_ok() {
        List<String> data = fileReader.read(EMPTY_FILE_PATH);
        assertEquals(0, data.size());
    }

    @Test
    public void readFile_invalidFilePath_throwsException() {
        assertThrows(RuntimeException.class, () -> fileReader.read(INVALID_FILE_PATH),
                "Expected to throw RuntimeException for invalid file path");
    }
}
