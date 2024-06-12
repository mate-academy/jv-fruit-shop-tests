package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String INPUT_FILE_PATH = "src/main/resources/reportToRead.csv";
    private static final String NON_EXISTING_FILE_PATH = "src/main/resources/nonExistingFile.txt";
    private static final FileReaderService fileReader = new FileReaderImpl();

    @Test
    public void test_readFile_successful() {
        List<String> lines = fileReader.read(INPUT_FILE_PATH);

        assertNotNull(lines);
        assertFalse(lines.isEmpty());
    }

    @Test
    public void test_readNonExistingFile_throwsException() {
        assertThrows(RuntimeException.class, () -> fileReader.read(NON_EXISTING_FILE_PATH));
    }
}
