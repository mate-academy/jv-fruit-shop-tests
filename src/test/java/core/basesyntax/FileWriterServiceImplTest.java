package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private final WriterService writerService = new FileWriterServiceImpl();

    @Test
    void writeToFile_validInput_success() {
        String testReport = "Sample Report";
        String testReportFile = "TestReport.txt";
        Path pathToTestReport = Path.of(testReportFile);
        String expectedReport = reader(testReportFile);

        writerService.writeToFile(testReportFile, testReport);
        assertTrue(Files.exists(pathToTestReport));
        assertEquals(testReport, expectedReport);
    }

    @Test
    void writeToFile_invalidPath_throwsException() {
        String invalidPath = "\0invalidPath.txt";
        String testReport = "Sample Report";

        assertThrows(RuntimeException.class, () ->
                writerService.writeToFile(invalidPath, testReport));
    }

    private String reader(String pathToFile) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(pathToFile))) {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + pathToFile, e);
        }
    }
}
