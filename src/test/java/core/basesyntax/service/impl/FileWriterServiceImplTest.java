package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;

    @BeforeAll
    static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    void write_validPath_ok() {
        Path tempFile;
        try {
            tempFile = Files.createTempFile("test", ".csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String filePath = tempFile.toString();
        String expectedContent = "type,fruit,quantity b,banana,20 b,apple,100";
        fileWriterService.write(expectedContent, filePath);
        String actualContent;
        try {
            actualContent = Files.readString(tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expectedContent, actualContent);
        assertTrue(Files.exists(tempFile));
        try {
            Files.delete(tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void write_invalidPath_notOk() {
        String content = "qwerty123";
        String invalidPath = "src/test/resources/nonExistentDir/File.csv";
        assertThrows(RuntimeException.class,
                () -> fileWriterService.write(content, invalidPath));
    }
}
