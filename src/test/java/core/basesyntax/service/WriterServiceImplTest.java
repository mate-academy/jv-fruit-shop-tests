package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class WriterServiceImplTest {

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
        String correctUrl = "src/test/resources/WriterServiceImpl/correctoutput.csv";
        String forEqualsUrl = "src/test/resources/WriterServiceImpl/forequals.csv";
        WriterService writerService = new WriterServiceImpl();
        Path correctUrlPath = Paths.get(correctUrl);
        Path forEqualsUrlPath = Paths.get(correctUrl);
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
        String incorrectUrl = "src/test/resources/WriterServiceImpl/nosuchfile.csv";
        Path path = Paths.get(incorrectUrl);
        new ReaderServiceImpl().read(path);
    }
}
