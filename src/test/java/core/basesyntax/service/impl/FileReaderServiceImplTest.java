package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final FileReaderServiceImpl fileReader = new FileReaderServiceImpl();
    private static final String EMPTY_FILE = "src" + File.separator + "test" + File.separator
            + "resources" + File.separator + "Empty.csv";
    private static final String CORRECT_FILE = "src" + File.separator + "test" + File.separator
            + "resources" + File.separator + "Correct.csv";

    @Test
    void readFromFile_EmptyFile_Ok() {
        String[] expected = new String[0];
        String[] actual = fileReader.readFromFile(EMPTY_FILE);
        assertArrayEquals(expected, actual);
    }

    @Test
    void readFromFile_NonExistingFile_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.readFromFile("File.csv"));
    }

    @Test
    void readFromFile_CorrectFile_Ok() {
        String[] expected = new String[] { "b,banana,30", "b,apple,200",};
        String[] actual = fileReader.readFromFile(CORRECT_FILE);
        assertArrayEquals(expected, actual);
    }

    @Test
    void readFromFile_NullValues_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.readFromFile(null));
    }
}
