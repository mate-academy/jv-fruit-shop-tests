package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {

    private static String correctUrl = "src/test/resources/WriterServiceImpl/correctoutput.csv";
    private static String forEqualsUrl = "src/test/resources/WriterServiceImpl/forequals.csv";
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_correctFilePath_ok() {
        StringBuilder stringToWrite = new StringBuilder();
        stringToWrite.append("type,fruit,quantity").append(System.lineSeparator())
                .append("b,banana,20").append(System.lineSeparator())
                .append("b,apple,100").append(System.lineSeparator())
                .append("s,banana,100").append(System.lineSeparator())
                .append("p,banana,13").append(System.lineSeparator())
                .append("r,apple,10").append(System.lineSeparator())
                .append("p,apple,20").append(System.lineSeparator())
                .append("p,banana,5").append(System.lineSeparator())
                .append("s,banana,50");
        Path correctUrlPath = Paths.get(correctUrl);
        Path forEqualsUrlPath = Paths.get(forEqualsUrl);
        writerService.write(correctUrlPath, stringToWrite.toString());
        List<String> actual;
        try {
            actual = Files.readAllLines(correctUrlPath);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file" + correctUrlPath, e);
        }
        List<String> expected;
        try {
            expected = Files.readAllLines(forEqualsUrlPath);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file" + forEqualsUrl, e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_incorrectFilePath_notOk() {
        Path path = Paths.get(null);
        writerService.write(path, "123");
    }
}
