package core.basesyntax.service;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String PATH_TO_OUTPUT_FILE
            = "src/test/java/core/basesyntax/resources/output.csv";
    private static FileWriter fileWriter;

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("fruit, quantity")
                .append(System.lineSeparator())
                .append("banana,132")
                .append(System.lineSeparator())
                .append("apple,90");
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_wrongFile_notOk() {
        StringBuilder reportBuilder = null;
        String wrongPath = "wrong/file.csv";
        fileWriter.writeToFile("", wrongPath);
    }

    @Test
     public void writeToFile_correctFile_ok() throws IOException {
        StringBuilder reportBuilder = new StringBuilder();
        fileWriter.writeToFile(reportBuilder.toString(), PATH_TO_OUTPUT_FILE);
        List<String> expected = Files.readAllLines(Path.of(PATH_TO_OUTPUT_FILE));
        List<String> actual = Files.readAllLines(Path.of(PATH_TO_OUTPUT_FILE));
        Assert.assertEquals(expected, actual);
    }
}
