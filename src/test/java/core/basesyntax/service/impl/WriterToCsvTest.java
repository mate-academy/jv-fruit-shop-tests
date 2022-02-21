package core.basesyntax.service.impl;

import core.basesyntax.service.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterToCsvTest {
    private static final String FILE = "src/test/resources/report.csv";
    private static final String WRONG_FILE = "src/test/badResources/report.csv";
    private static final String DATA = "fruit,quantity\n"
            + "apple,90\n"
            + "banana,132";
    private static Writer writer;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writer = new WriterToCsv();
    }

    @Test
    public void writeDataToFile_ok() {
        String actual;
        writer.writeToReport(DATA, FILE);
        try {
            actual = Files.readAllLines(Path.of(FILE)).stream()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
        Assert.assertEquals(DATA, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeDataToFile_notOk() {
        writer.writeToReport(DATA, WRONG_FILE);
    }
}
