package service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterImplTest {
    private static StringBuilder stringBuilder;
    private static final String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();
    private static final String OUTPUT_FILE = "src" + FILE_SEPARATOR
            + "test" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "testOutput.csv";
    private static Writer writer;
    private static StringBuilder reportBuilder;

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterImpl();
        reportBuilder = new StringBuilder();
        reportBuilder.append("fruit,quantity");
        reportBuilder.append(System.lineSeparator());
        reportBuilder.append("banana,172");
        reportBuilder.append(System.lineSeparator());
        reportBuilder.append("apple,190");
    }

    @Test
    public void writeValidData_Ok() {
        String data = reportBuilder.toString();
        writer.write(data, OUTPUT_FILE);
        String actualResult;
        try {
            List<String> actual = Files.readAllLines(Path.of(OUTPUT_FILE));
            actualResult = actual.stream().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file "
                    + OUTPUT_FILE);
        }
        Assert.assertEquals(data, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void invalidFilePath_NotOk() {
        String data = reportBuilder.toString();
        writer.write(data, "");
    }

    @Test (expected = RuntimeException.class)
    public void writeNullPath() {
        writer.write(stringBuilder.toString(), null);
    }
}
