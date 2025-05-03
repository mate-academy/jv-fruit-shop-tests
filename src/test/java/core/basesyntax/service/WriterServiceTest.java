package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static WriterService writer;
    private static final String PATH_OF_TEST_FILE = "src/main/resources/testWriter.csv";

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullPathFile_NotOk() {
        writer.writeToFile("Same data", null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullReport_NotOk() {
        writer.writeToFile(null,PATH_OF_TEST_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_FilePathWithNotCsvFormat_NotOk() {
        writer.writeToFile("hello", "src/main/resources/testWriter.txt");
    }

    @Test
    public void writeToFile_ValidData_Ok() throws IOException {
        String data = new StringBuilder().append("banana")
                .append(System.lineSeparator()).append("peach").toString();
        writer.writeToFile(data, PATH_OF_TEST_FILE);
        List<String> expected = List.of("banana", "peach");
        List<String> actual = Files.readAllLines(Path.of(PATH_OF_TEST_FILE));
        Assert.assertEquals(expected, actual);
    }
}
