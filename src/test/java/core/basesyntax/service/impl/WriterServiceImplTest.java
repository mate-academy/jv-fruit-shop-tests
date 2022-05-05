package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;
    private static String outputFile;
    private static String report;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
        outputFile = "src/test/resources/outputTest.csv";
        report = "fruit,quantity" + System.lineSeparator()
                + "banana,183" + System.lineSeparator()
                + "apple,49" + System.lineSeparator();
    }

    @Test
    public void writeToFile_Ok() {
        writerService.writeToFile(outputFile, report);
        List<String> actual;
        try {
            actual = Files.readAllLines(Paths.get(outputFile));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from the file " + outputFile, e);
        }
        List<String> expected = List.of("fruit,quantity",
                "banana,183",
                "apple,49");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToInvalidPath_notOk() {
        writerService.writeToFile("src/test/java/invalidPath/outputTest.csv", report);
    }

    @Test(expected = RuntimeException.class)
    public void writeToPathWithWhitespace_notOk() {
        writerService.writeToFile(" src/test/ resources/outputTest.csv", report);
    }
}
