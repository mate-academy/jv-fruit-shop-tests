package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final String REPORT = "fruit,quantity" + System.lineSeparator() + "banana,152"
            + System.lineSeparator() + "apple,90" + System.lineSeparator();
    private static final String PATH_TO_FILE = "src/test/resources/report.csv";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void recordReportInFile_Ok() {
        fileWriterService.writeToFile(REPORT, PATH_TO_FILE);
        try {
            String actual = Files.readString(Path.of(PATH_TO_FILE));
            Assert.assertEquals(REPORT, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }
}
