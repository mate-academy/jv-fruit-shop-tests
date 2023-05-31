package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvWriterServiceImplTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/testReport.csv";
    private static final String CORRECT_REPORT = "correct";
    private static FileWriterService writerService;

    @BeforeClass
    public static void init() {
        writerService = new CsvWriterServiceImpl();
    }

    @Test
    public void write_correctFilePath_ok() {
        writerService.write(CORRECT_FILE_PATH, CORRECT_REPORT);
        try {
            Assert.assertEquals(CORRECT_REPORT, Files.readString(Path.of(CORRECT_FILE_PATH)));
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test(expected = RuntimeException.class)
    public void write_fileNotFound_notOk() {
        String fileName = "notFound/notfound.cvs";
        writerService.write(fileName, CORRECT_REPORT);
    }

    @Test(expected = NullPointerException.class)
    public void write_nullFilePath_notOk() {
        writerService.write(null, CORRECT_REPORT);
    }

    @Test(expected = NullPointerException.class)
    public void write_nullReport_notOk() {
        writerService.write(CORRECT_FILE_PATH, null);
    }
}
