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
    private static WriterService writerService;

    @BeforeClass
    public static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_folderWithFileDoesNotExist_notOk() {
        String fromFileName = "src/test/unknownFolder/OutputTestData.csv";
        String testedInfo = "fruit,quantity";
        writerService.writeFile(fromFileName, testedInfo);
    }

    @Test
    public void writeFile_ok() {
        String fromFileName = "src/test/resources/OutputTestData.csv";
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        writerService.writeFile(fromFileName, expected);
        List<String> temp;
        try {
            temp = Files.readAllLines(Path.of(fromFileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from list" + fromFileName, e);
        }
        StringBuilder sb = new StringBuilder();
        for (String string: temp) {
            sb.append(string).append(System.lineSeparator());
        }
        String actual = sb.toString();
        Assert.assertEquals(expected,actual);
    }
}
