package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String PATH = "src/test/resources/test_report.csv";
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_ok() {
        String expected = null;
        String actual = "fruit,quantity";
        writerService.writeToFile(PATH, actual);
        try {
            expected = Files.readString(Path.of(PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullPath_notOk() {
        writerService.writeToFile(null, "fruit,quantity");
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_wrongPath_notOk() {
        String wrongPath = "src/main/resources/smth/report.csv";
        writerService.writeToFile(wrongPath, "fruit,quantity");
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullReport_notOk() {
        writerService.writeToFile(PATH, null);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullValues_notOk() {
        writerService.writeToFile(null, null);
    }
}
