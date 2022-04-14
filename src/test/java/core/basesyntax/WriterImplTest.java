package core.basesyntax;

import core.basesyntax.service.Writer;
import core.basesyntax.service.impl.WriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterImplTest {
    private static final String TO_FILE_PATH =
            "src/test/java/core/basesyntax/resources/report.csv";
    private static final String INPUT_DATA = "fruit,quantity"
            + System.lineSeparator()
            + "banana,14"
            + System.lineSeparator()
            + "grape,8"
            + System.lineSeparator();
    private static Writer writer;

    @BeforeClass
    public static void initialization() {
        writer = new WriterImpl();
    }

    @Test
    public void write_writeToValidPath() {
        List<String> expected = List.of(INPUT_DATA.split(System.lineSeparator()));
        List<String> actual;
        writer.write(INPUT_DATA, TO_FILE_PATH);
        try {
            actual = Files.readAllLines(Path.of(TO_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from a file " + TO_FILE_PATH, e);
        }
        Assert.assertEquals(expected, actual);
    }
}
