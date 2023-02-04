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
    private static String outputFilePath;
    private static WriterService writerService;
    private static String reportInfo;
    private static List<String> expectedList;

    @BeforeClass
    public static void beforeClass() {
        outputFilePath = "src/test/java/resources/OutputInfo.csv";
        writerService = new WriterServiceImpl();
        reportInfo = "fruit,quantity" + System.lineSeparator()
                + "banana,120" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        expectedList = List.of("fruit,quantity", "banana,120", "apple,100");
    }

    @Test
    public void writeInfoToFile_reportInfo_Ok() {
        writerService.writeInfoToFile(reportInfo, outputFilePath);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(outputFilePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + outputFilePath, e);
        }
        assertEquals(actual, expectedList);
    }

    @Test(expected = NullPointerException.class)
    public void writeInfoToFile_nullInfo_notOk() {
        writerService.writeInfoToFile(null, outputFilePath);
    }

    @Test(expected = NullPointerException.class)
    public void writeInfoToFile_nullPath_notOk() {
        writerService.writeInfoToFile(reportInfo, null);
    }
}
