package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterServiceImplTest {
    private final WriterService writerService = new FileWriterServiceImpl();

    @Test
    void writeToFile_validInput_success(@TempDir Path tempDir) throws IOException {
        String testReport = "Sample Report";
        Path testFile = tempDir.resolve("testReport.txt");
        writerService.writeToFile(testFile.toString(), testReport);
        assertTrue(Files.exists(testFile));
        assertEquals(testReport, Files.readString(testFile));
    }

    @Test
    void writeToFile_invalidPath_throwsException() {
        String invalidPath = "\0invalidPath.txt";
        String testReport = "Sample Report";

        assertThrows(RuntimeException.class, () ->
                writerService.writeToFile(invalidPath, testReport));
    }
}
