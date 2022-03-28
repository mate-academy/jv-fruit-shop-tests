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
    private static final String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();
    private static final String OUTPUT_FILE = "src" + FILE_SEPARATOR
            + "test" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "testOutput.csv";
    private static final String INVALID_PATH = "";
    private static Writer writer;
    private static StringBuilder builder;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writer = new WriterImpl();
        builder = new StringBuilder();
        builder.append("fruit,quantity");
        builder.append(System.lineSeparator());
        builder.append("banana,172");
        builder.append(System.lineSeparator());
        builder.append("apple,190");
    }

    @Test
    public void writeValidData_OK() {
        String data = builder.toString();
        writer.write(data, OUTPUT_FILE);
        String actualResult = new String();
        try {
            List<String> actual = Files.readAllLines(Path.of(OUTPUT_FILE));
            actualResult = actual.stream().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + OUTPUT_FILE);
        }
        String expectedResult = new String(data);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void invalidFilePath_NotOK() {
        String data = builder.toString();
        Exception exception = new Exception();
        try {
            writer.write(data, INVALID_PATH);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertEquals(
                String.format("For an invalid file path %s should be thrown, but was %s\n",
                        RuntimeException.class, exception.getClass()),
                RuntimeException.class, exception.getClass());
    }
}
