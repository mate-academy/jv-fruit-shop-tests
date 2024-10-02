package core.basesyntax.util.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.util.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String INVALID_PATH = "invalid/path.txt";
    private FileReader fileReader = new FileReaderImpl();

    @Test
    public void readFile_invalidFilePath_throwsException() {
        assertThrows(IOException.class, () -> fileReader.read(INVALID_PATH));
    }
}
