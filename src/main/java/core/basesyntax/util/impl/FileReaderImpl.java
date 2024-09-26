package core.basesyntax.util.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private FileReaderImpl fileReader;

    @BeforeEach
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFile_validFilePath_ok() throws IOException {
        String expected = "test content";
        String actual = fileReader.read("src/test/resources/testFile.txt");
        assertEquals(expected, actual);
    }

    @Test
    public void readFile_invalidFilePath_throwsException() {
        assertThrows(IOException.class, () -> fileReader.read("invalid/path.txt"));
    }
}
