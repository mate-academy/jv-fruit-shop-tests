package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static CsvFileWriterService writerService;
    private static final String FILE_PATH = "src/test/resources/rp.csv";

    @BeforeClass
    public static void setUp() {
        writerService = new CsvFileWriterServiceImpl();
    }

    @Test
    public void writerToFile_pathAndReportValid_ok() {
        String expected = "Random text";
        writerService.writerToFile(Path.of(FILE_PATH), expected);
        String actual;
        try {
            actual = Files.readString(Path.of(FILE_PATH));

        } catch (IOException e) {
            throw new RuntimeException("Invalid path to report file", e);
        }
        Assert.assertEquals("Incorrect data in report", expected, actual);
    }
}
