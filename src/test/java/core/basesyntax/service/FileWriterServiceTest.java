package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceTest {
    private static final String TEST_CONTENT = "This is a test file.";
    private FileWriterService writer;

    @BeforeEach
    void setUp() {
        writer = new FileWriterServiceImpl();
    }

    @Test
    void writeToFile_Test() throws IOException {
        Path tempFile = Files.createTempFile("test", ".txt");
        writer.writeToFile(TEST_CONTENT, tempFile.toString());

        String expectedContent = TEST_CONTENT;
        String actualContent = Files.readString(new File(tempFile.toUri()).toPath());
        assertEquals(expectedContent, actualContent);

        Files.delete(tempFile);
    }
}
