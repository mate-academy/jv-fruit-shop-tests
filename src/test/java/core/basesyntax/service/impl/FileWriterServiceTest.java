package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

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
    public void writeEmptyContentThrowsIllegalArgumentException() {
        String content = "";
        String filePath = "src/test/resources/output.csv";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fileWriterService.write(content, filePath);
        });

        assertEquals("Content cannot be empty", exception.getMessage());
    }
}
