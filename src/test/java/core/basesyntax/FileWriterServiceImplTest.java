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
import org.junit.jupiter.api.io.TempDir;

class FileWriterServiceImplTest {
    private static final String invalidPath = "\0invalidPath.txt";
    private static final String validPath = "TestReport.txt";
    private static final String testReport = "Sample Report";
    private final WriterService writerService = new FileWriterServiceImpl();

    @Test
    void writeToFile_validInput_success(@TempDir Path tempDir) {
        Path pathToTestReport = tempDir.resolve(validPath);
        writerService.writeToFile(pathToTestReport.toString(), testReport);
        String actualReport = "";
        try {
            actualReport = Files.readString(pathToTestReport);
        } catch (IOException e) {
            fail("Can`t read from file");
        }
        assertTrue(Files.exists(pathToTestReport));
        assertEquals(testReport, actualReport);
    }

    @Test
    void writeToFile_invalidPath_throwsException() {
        String expectedMessage = "Can`t write to file " + invalidPath;
        RuntimeException e = assertThrows(RuntimeException.class, () ->
                writerService.writeToFile(invalidPath, testReport));
        assertEquals(expectedMessage, e.getMessage());
    }
}
