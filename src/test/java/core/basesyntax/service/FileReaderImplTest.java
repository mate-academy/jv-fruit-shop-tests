package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.FileReaderImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderImplTest {

    private static final String FILE_NAME = "test_file.txt";

    @TempDir
    private File tempDir;

    private FileReaderImpl fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validFile_ok() throws IOException {
        File file = new File(tempDir, FILE_NAME);
        String content = "first line\nsecond line\nthird line";
        Files.writeString(file.toPath(), content);

        List<String> result = fileReader.read(file.getAbsolutePath());

        assertEquals(3, result.size());
        assertEquals("first line", result.get(0));
        assertEquals("second line", result.get(1));
        assertEquals("third line", result.get(2));
    }

    @Test
    void read_fileWithEmptyLines_ok() throws IOException {
        File file = new File(tempDir, FILE_NAME);
        String content = "line one\n\nline two\n\n";
        Files.writeString(file.toPath(), content);

        List<String> result = fileReader.read(file.getAbsolutePath());

        assertEquals(2, result.size());
        assertTrue(result.contains("line one"));
        assertTrue(result.contains("line two"));
    }

    @Test
    void read_nonExistingFile_throwIoException() {
        File nonExistingFile = new File(tempDir, "non_existing.txt");
        assertThrows(IOException.class, () -> fileReader.read(nonExistingFile.getAbsolutePath()));
    }
}
