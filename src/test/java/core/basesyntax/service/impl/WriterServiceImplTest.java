package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String OUTPUT_FILE_PATH = "src/test/java/resources/OutputInfo.csv";
    private static WriterService writerService;
    private static String reportInfo;
    private static List<String> expectedList;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
        reportInfo = "fruit,quantity" + System.lineSeparator()
                + "banana,120" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        expectedList = List.of("fruit,quantity", "banana,120", "apple,100");
    }

    @Test
    public void writeInfoToFile_reportInfo_Ok() {
        writerService.writeInfoToFile(reportInfo, OUTPUT_FILE_PATH);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + OUTPUT_FILE_PATH, e);
        }
        assertEquals(actual, expectedList);
    }

    @Test(expected = NullPointerException.class)
    public void writeInfoToFile_nullInfo_notOk() {
        writerService.writeInfoToFile(null, OUTPUT_FILE_PATH);
    }

    @Test(expected = NullPointerException.class)
    public void writeInfoToFile_nullPath_notOk() {
        writerService.writeInfoToFile(reportInfo, null);
    }
}
