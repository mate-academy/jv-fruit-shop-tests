package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private final WriterService writerService = new FileWriterServiceImpl();

    @Test
    void writeToFile_validInput_success() {
        String testReport = "Sample Report";
        File testFile = new File("testReport.txt");
        Path testReportFile = Path.of(testFile.toURI());
        String expectedReport = null;
        try {
            expectedReport = Files.readString(testReportFile);
        } catch (IOException e) {
            fail("Error reading file: " + e.getMessage());
        }
        writerService.writeToFile(testFile.toString(), testReport);
        assertTrue(Files.exists(testReportFile));
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
