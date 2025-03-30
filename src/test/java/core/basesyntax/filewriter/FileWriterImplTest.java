package core.basesyntax.filewriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {
    @TempDir
    private Path tempDir;

    private final FileWriterImpl fileWriter = new FileWriterImpl();

    public Path getTempDir() {
        return tempDir;
    }

    public void setTempDir(Path tempDir) {
        this.tempDir = tempDir;
    }

    public FileWriterImpl getFileWriter() {
        return fileWriter;
    }

    @Test
    void write_ValidInput_CreatesCorrectFile() throws IOException {
        Path tempFile = tempDir.resolve("output.csv");
        String inputReport = "apple,10 banana,5";

        fileWriter.write(inputReport, tempFile.toString());

        // Assert
        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(3, lines.size());
        assertEquals("fruit,quantity", lines.get(0));
        assertEquals("apple,10", lines.get(1));
        assertEquals("banana,5", lines.get(2));
    }

    @Test
    void write_EmptyInput_WritesOnlyHeader() throws IOException {
        Path tempFile = tempDir.resolve("empty_output.csv");

        fileWriter.write("", tempFile.toString());

        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(2, lines.size());
        assertEquals("fruit,quantity", lines.get(0));
    }

    @Test
    void write_InvalidPath_ThrowsException() {
        String invalidPath = "/invalid/directory/output.csv";

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileWriter.write("apple,10", invalidPath));
        assertTrue(exception.getMessage().contains("Error while reading the file."));
    }
}
