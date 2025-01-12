package core.basesyntax.filewriter;

import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileWriterImplTest {
    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @Test
    void write_validFile_ok() throws Exception {
        File tempFile = File.createTempFile("output", ".csv");
        String data = "line1\nline2\nline3";
        fileWriter.write(data, tempFile.getPath());
        String result = Files.readString(tempFile.toPath());
        assertEquals(data, result);
    }

    @Test
    void write_invalidPath_throwsException() {
        assertThrows(RuntimeException.class, () ->
                fileWriter.write("data", "invalid/path.csv"));
    }
}
