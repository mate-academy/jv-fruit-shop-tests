package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterImplTest {
    private static Writer writer;
    private static StringBuilder reportBuilder;

    @BeforeClass
    public static void beforeClass() throws Exception {
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
        writer.write(data, "src/test/resources/testOutput.csv");
        String actualResult = new String();
        try {
            List<String> actual = Files.readAllLines(Path.of("src/test/resources/testOutput.csv"));
            actualResult = actual.stream().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file "
                    + "src/test/resources/testOutput.csv");
        }
        String expectedResult = new String(data);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void invalidFilePath_NotOk() {
        String data = reportBuilder.toString();
        writer.write(data, "");
    }
}
