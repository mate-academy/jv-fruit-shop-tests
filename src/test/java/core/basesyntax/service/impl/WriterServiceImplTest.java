package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String INPUT_FILE_NAME = "src/test/resources/writerTestInput.csv";
    private static final String READ_FILE_NAME = "src/test/resources/actualInput.csv";
    private static String writeDataToFile;
    private static List<String> readCorrectData;
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
        readCorrectData =
                List.of("type,fruit,quantity",
                        "b,banana,20",
                        "b,apple,100",
                        "s,banana,100",
                        "p,banana,13",
                        "r,apple,10",
                        "p,apple,20",
                        "p,banana,5",
                        "s,banana,50");
        writeDataToFile =
                "type,fruit,quantity" + System.lineSeparator()
                        + "b,banana,20" + System.lineSeparator()
                        + "b,apple,100" + System.lineSeparator()
                        + "s,banana,100" + System.lineSeparator()
                        + "p,banana,13" + System.lineSeparator()
                        + "r,apple,10" + System.lineSeparator()
                        + "p,apple,20" + System.lineSeparator()
                        + "p,banana,5" + System.lineSeparator()
                        + "s,banana,50";
    }

    @Test(expected = RuntimeException.class)
    public void writer_fileNameNull_notOk() {
        writerService.writeToFile(null, "");
    }

    @Test
    public void writer_dataEqualsFromFile_ok() {
        writerService.writeToFile(INPUT_FILE_NAME, writeDataToFile);
        List<String> actual = readFromFile(READ_FILE_NAME);
        Assert.assertEquals(readCorrectData, actual);
    }

    @Test(expected = NullPointerException.class)
    public void writer_fileReportNull_notOk() {
        writerService.writeToFile(INPUT_FILE_NAME, null);
    }

    @Test(expected = RuntimeException.class)
    public void writer_illegalPath_notOk() {
        writerService.writeToFile("", writeDataToFile);
    }

    private List<String> readFromFile(String fileName) {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + fileName + " ", e);
        }
    }
}
