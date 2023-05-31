package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;
    private String report = "banana,15";

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writerServiceIsValid_Ok() {
        writerService.writeToFile(report,"src/test/java/resources/report");
        String actual = "";
        String expected = report;
        try {
            actual = String.join("", Files.readAllLines(Path.of("src/test/java/resources/report")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(expected, actual);
    }
}
