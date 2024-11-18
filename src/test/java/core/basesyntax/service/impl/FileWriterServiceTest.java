package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterServiceTest {

    private FileWriterService fileWriterService;

    @BeforeEach
    public void setUp() {
        fileWriterService = new FileWriterImpl();
    }

    @Test
    public void write_validData_success() throws IOException {
        String content = "fruit,quantity\nbanana,100\napple,50";
        String filePath = "src/main/resources/output.csv";

        Files.deleteIfExists(Paths.get(filePath));
        fileWriterService.write(content, filePath);

        File file = new File(filePath);

        String writtenContent = new String(Files.readAllBytes(Paths.get(filePath)));
        assertEquals("The file content should match the expected.", content, writtenContent);
    }

    @Test
    public void writeEmptyContentThrowsIllegalArgumentException() {
        String content = "";
        String filePath = "src/main/resources/output.csv";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fileWriterService.write(content, filePath);
        });

        assertEquals("Content cannot be empty", exception.getMessage());
    }

    @Test
    public void writeNullContentThrowsIllegalArgumentException() {
        String content = null;
        String filePath = "src/main/resources/output.csv";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fileWriterService.write(content, filePath);
        });

        assertEquals("Content cannot be empty", exception.getMessage());
    }
}

