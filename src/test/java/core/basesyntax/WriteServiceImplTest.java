package core.basesyntax;

import core.basesyntax.service.WriteService;
import core.basesyntax.service.impl.WriteServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriteServiceImplTest {
    private static final String PATH = "src/test/resources/report.csv";
    private static final String INPUT_STRING = "fruit,quantity"
            + "\n"
            + "banana,34"
            + "\n"
            + "mango,11"
            + "\n";
    private static WriteService writeService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void initialization() {
        writeService = new WriteServiceImpl();
    }

    @Test
    public void write_writeToValidPath() {
        List<String> expected = List.of(INPUT_STRING.split("\n"));
        List<String> actual;
        writeService.write(INPUT_STRING, PATH);
        try {
            actual = Files.readAllLines(Path.of(PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + PATH + e);
        }
        Assert.assertEquals(expected, actual);
    }
}
