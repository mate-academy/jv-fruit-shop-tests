package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.interfaces.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class WriterServiceImplTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    private WriterService writerService;

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_ok() throws IOException {
        File tempFile = tempFolder.newFile("tempFile.txt");
        List<String> lines = Arrays.asList("Line 1", "Line 2", "Line 3");
        writerService.writeToFile(tempFile.getAbsolutePath(), lines);
        List<String> writtenLines = Files.readAllLines(tempFile.toPath());
        assertEquals(lines, writtenLines);
    }
}
