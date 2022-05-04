package service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import service.impl.FileWriterImpl;

public class FileWriterTest {
    private static final String OUTPUT_PATH = "src/test/resources/outputTest.csv";
    private static final String INPUT_PATH = "src/test/resources/inputValidTest.csv";
    private static final StringBuilder builder = new StringBuilder();
    private static FileWriter fileWriter;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
        builder.append("type,fruit,quantity")
                .append(System.lineSeparator()).append("b,banana,30")
                .append(System.lineSeparator()).append("b,apple,300")
                .append(System.lineSeparator()).append("s,banana,50")
                .append(System.lineSeparator()).append("p,banana,44")
                .append(System.lineSeparator()).append("r,apple,5")
                .append(System.lineSeparator()).append("p,apple,4")
                .append(System.lineSeparator()).append("p,banana,7")
                .append(System.lineSeparator()).append("s,banana,4");
    }

    @Test
    public void emptyDataWriter_Ok() {
        fileWriter.writeToFile(OUTPUT_PATH, "");
        List<String> expected = List.of();
        List<String> actual = readerForTest(OUTPUT_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void validDataWriter_Ok() {
        fileWriter.writeToFile(OUTPUT_PATH, builder.toString());
        List<String> expected = readerForTest(INPUT_PATH);
        List<String> actual = readerForTest(OUTPUT_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeInEmptyPath_NotOk() {
        fileWriter.writeToFile("", builder.toString());
    }

    private List<String> readerForTest(String fromFilePath) {
        List<String> data;
        try {
            data = Files.readAllLines(Path.of(fromFilePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + fromFilePath, e);
        }
        return data;
    }
}
