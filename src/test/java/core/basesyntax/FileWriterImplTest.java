package core.basesyntax;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writer_fileCreated_Ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        String fileName = "src/test/resources/result_test.csv";
        fileWriter.writeToFile(fileName, report);
        Assert.assertTrue(Files.exists(Path.of(fileName)));
    }

    @Test
    public void writer_fileWritten_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        List<String> expectedList = Arrays.asList(expected.split(System.lineSeparator()));
        String fileName = "src/test/resources/result_test.csv";
        fileWriter.writeToFile(fileName, expected);
        List<String> actualList;
        try {
            actualList = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Error while running the test", e);
        }
        Assert.assertEquals(expectedList,actualList);
    }

    @Test(expected = RuntimeException.class)
    public void writer_writeToFile_notOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        new FileWriterImpl()
                .writeToFile("", report);
    }
}
