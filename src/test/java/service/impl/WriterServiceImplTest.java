package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import service.WriterService;

public class WriterServiceImplTest {

    private final WriterService writerService = new WriterServiceImpl();

    @Test
    void writeToFile_writesContentSuccessfully() throws IOException {
        Path tempFile = Files.createTempFile("report", ".txt");
        String report = "fruit,quantity\napple,10\nbanana,5";

        writerService.writeToFile(report, tempFile.toString());

        String content = Files.readString(tempFile);
        assertEquals(report, content);
    }

    @Test
    void writeToFile_invalidPath_throwsRuntimeException() {
        String invalidPath = "/invalid_path/report.txt";
        String report = "fruit,quantity";

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> writerService.writeToFile(report, invalidPath)
        );
        assertTrue(exception.getMessage().contains("Can't write report to file"));
    }
}

