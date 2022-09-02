package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String REPORT = "banana,36";
    private static final String OUTPUT_PATH = "src/test/resources/output.csv";
    private FileWriterService fileWriterService;

    @Before
    public void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void write_To_File_OK() {
        fileWriterService.writeToFile(REPORT, OUTPUT_PATH);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(OUTPUT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file");
        }
        Assert.assertEquals(REPORT, actual.get(0));
    }

    @Test(expected = NullPointerException.class)
    public void write_To_File_Not_OK() {
        fileWriterService.writeToFile("", null);
    }

}
