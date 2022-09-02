package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String REPORT = "banana,36";
    private static final String OUTPUT_PATH = "src/test/resources/output.csv";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_validData_OK() {
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
    public void writeToFile_NullPath_NotOK() {
        fileWriterService.writeToFile("", null);
    }

}
