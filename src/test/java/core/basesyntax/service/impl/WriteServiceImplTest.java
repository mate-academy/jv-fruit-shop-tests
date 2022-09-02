package core.basesyntax.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WriteServiceImplTest {
    private WriteServiceImpl writeService;
    private String fileName;

    @Before
    public void setUp() {
        writeService = new WriteServiceImpl();
        fileName = "Output.csv";
    }

    @Test
    public void writeToFileOk() throws IOException {
        String report = "fruit,quantity" + System.lineSeparator() + "b,banana,88";
        writeService.writeToFile(fileName, report);
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("b,banana,88");
        List<String> actual = Files.readAllLines(Path.of(fileName));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writerService_writeFileWithNullReport_NotOk() {
        writeService.writeToFile(fileName, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeServiceFileNameNullNotOk() {
        writeService.writeToFile(null, "");
    }

}
