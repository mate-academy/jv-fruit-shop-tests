package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class FileWriterServiceTest {

    private FileWriterService fileWriterService = new FileWriterImpl();

    @Test
    public void write_validData_success() throws IOException {
        String content = "fruit,quantity\nbanana,100\napple,50";
        String filePath = "src/test/resources/output.csv";

        fileWriterService.write(content, filePath);

        File file = new File(filePath);
        assertTrue(file.exists());
    }

    @Test
    public void write_emptyContent_throwsIOException() {
        String content = "";
        String filePath = "src/test/resources/output.csv";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fileWriterService.write(content, filePath);
        });

        assertEquals("Content cannot be empty", exception.getMessage());
    }
}
