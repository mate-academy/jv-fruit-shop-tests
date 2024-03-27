package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FileReadingException;
import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {

    private static final String EXISTING_FILE_PATH = "src/test/resources/existingFile.csv";
    private static final String NON_EXISTING_FILE_PATH = "src/test/resources/nonExistingFile.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";

    private static final FileReader fileReader = new FileReaderImpl();

    @Test
    void readFile_existingFile_isOk() throws IOException {
        List<String> actual = fileReader.readFile(EXISTING_FILE_PATH);
        List<String> expected = Files.readAllLines(Paths.get(EXISTING_FILE_PATH));
        assertEquals(expected, actual);
    }

    @Test
    void readFile_emptyFile_isOk() {
        List<String> actual = fileReader.readFile(EMPTY_FILE_PATH);
        List<String> expected = Arrays.asList();
        assertEquals(expected, actual);
    }

    @Test
    void readFile_nonExistingFile_notOk() {
        assertThrows(FileReadingException.class, () -> fileReader.readFile(NON_EXISTING_FILE_PATH));
    }
}
