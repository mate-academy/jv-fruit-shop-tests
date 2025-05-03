package core.fruitshop.service.impl;

import static org.junit.Assert.assertEquals;

import core.fruitshop.service.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String INVALID_PATH = "*/[][[313*&7652387/523|532";
    private static final String FILE_PATH = "src/test/java/resources/FileWriterTestFile.txt";
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    public void writeToFile_ok() throws IOException {
        String report = "fruit,quantity" + System.lineSeparator()
                + "apple,30" + System.lineSeparator()
                + "peach,130";
        fileWriter.writeToFile(report, FILE_PATH);
        List<String> actual = Files.readAllLines(Path.of(FILE_PATH));
        List<String> expected = List.of("fruit,quantity", "apple,30", "peach,130");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_noSuchFile_notOk() {
        fileWriter.writeToFile("", INVALID_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullData_notOk() {
        fileWriter.writeToFile(null, FILE_PATH);
    }

    @After
    public void tearDown() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(FILE_PATH));
        bufferedWriter.write("");
        bufferedWriter.close();
    }
}
