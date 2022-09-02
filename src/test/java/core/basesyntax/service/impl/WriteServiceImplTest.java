package core.basesyntax.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WriteServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/Output.csv";
    private static final String TEST_DATA = "fruit,quantity" + System.lineSeparator() + "banana,88";
    private static final List<String> EXPECTED_DATA = List.of("fruit,quantity", "banana,88");
    private WriteServiceImpl writeService;

    @Before
    public void setUp() {
        writeService = new WriteServiceImpl();
    }

    @Test
    public void writeToFileOk() throws IOException {
        writeService.writeToFile(FILE_PATH, TEST_DATA);
        List<String> actual = readFromFileInTest();
        Assert.assertEquals(EXPECTED_DATA, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writerService_writeFileWithNullReport_NotOk() {
        writeService.writeToFile(FILE_PATH, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeServiceFileNameNullNotOk() {
        writeService.writeToFile(null, "");
    }

    private List<String> readFromFileInTest() {
        List<String> list;
        try {
            list = Files.readAllLines(Path.of(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file: " + FILE_PATH, e);
        }
        return list;
    }
}
