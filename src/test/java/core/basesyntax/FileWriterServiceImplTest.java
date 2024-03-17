package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private final WriterService writerService = new FileWriterServiceImpl();

    @Test
    void writeToFile_validInput_success() {
        String testReport = "Sample Report";
        Path testFile = Path.of("testReport.txt");
        String expectedReport = null;
        try {
            expectedReport = Files.readString(testFile);
        } catch (IOException e) {
            fail("Error reading file: " + e.getMessage());
        }
        writerService.writeToFile(testFile.toString(), testReport);
        assertTrue(Files.exists(testFile));
        assertEquals(testReport, expectedReport);
    }

    @Test
    void writeToFile_invalidPath_throwsException() {
        String invalidPath = "\0invalidPath.txt";
        String testReport = "Sample Report";

        assertThrows(RuntimeException.class, () ->
                writerService.writeToFile(invalidPath, testReport));
    }
}
