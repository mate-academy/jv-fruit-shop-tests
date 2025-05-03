package core.basesyntax.service;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static FileWriter fileWriter;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void write_toCorrectFile_Ok() throws IOException {
        fileWriter.writeToFile("type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100", "src/test/resources/output_file.csv");
        List<String> actual = Files.readAllLines(
                Path.of("src/test/resources/output_file.csv"));
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        Assert.assertEquals(expected, actual);
    }
}
