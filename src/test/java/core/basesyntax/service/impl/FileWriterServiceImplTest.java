package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final Path FILE_PATH = Paths.get("src/test/resources/outputFile.txt");
    private final FileWriterService fileWriterService = new FileWriterServiceImpl();

    @Before
    public void setUp() throws IOException {
        Files.deleteIfExists(FILE_PATH);
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(FILE_PATH);
    }

    @Test
    public void testWriteToFile() throws IOException {
        String content = "This is a test content.";
        fileWriterService.writeToFile(content, FILE_PATH.toString());

        String fileContent = Files.readString(FILE_PATH);
        assertEquals(content, fileContent);
    }
}
