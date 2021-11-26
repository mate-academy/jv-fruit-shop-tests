package core.basesyntax.service.file.output;

import core.basesyntax.exception.MyCustomIoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterTest {
    private static final String OUTPUT_TEST_PATH = "src/test/resources/outputTestData.csv";
    private static final String INPUT_TEST_PATH = "src/test/resources/readForWriterTest.csv";
    private static final StringBuilder stringBuilder = new StringBuilder();
    private static Writer writer;

    @BeforeClass
    public static void beforeClass() {
        stringBuilder.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,100").append(System.lineSeparator())
                .append("apple,100").append(System.lineSeparator());
        writer = new WriterImpl();
    }

    @Test
    public void writeToFile_validDataAndPath_ok() {
        String report = stringBuilder.toString();
        writer.write(report, OUTPUT_TEST_PATH);
        List<String> actualList = readFromTestFile(OUTPUT_TEST_PATH);
        List<String> expectedList = readFromTestFile(INPUT_TEST_PATH);
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void writeToFile_emptyData_ok() {
        writer.write("", OUTPUT_TEST_PATH);
        List<String> actualList = readFromTestFile(OUTPUT_TEST_PATH);
        List<String> expectedList = List.of();
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void writeToFile_olyOneWhiteSpaceData_ok() {
        writer.write(" ", OUTPUT_TEST_PATH);
        List<String> actualList = readFromTestFile(OUTPUT_TEST_PATH);
        List<String> expectedList = List.of(" ");
        Assert.assertEquals(expectedList, actualList);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullPath_notOk() {
        String expected = stringBuilder.toString();
        writer.write(expected, null);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullData_notOk() {
        writer.write(null, OUTPUT_TEST_PATH);
    }

    @Test(expected = MyCustomIoException.class)
    public void writeToFile_emptyFilePath_notOk() {
        String expected = stringBuilder.toString();
        writer.write(expected, "");
    }

    private static List<String> readFromTestFile(String fileName) {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }

}
