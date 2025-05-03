package core.basesyntax;

import core.basesyntax.servises.FileWriter;
import core.basesyntax.servises.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String WRONG_PATH = "java/abcd.csv";
    private static final String COMPARE_PATH = "src/test/java/resources/outputTest.csv";
    private static final String REPORT = "fruit,quantity\n" + "107,banana\n" + "100,apple\n";
    private static final String CORRECT_PATH =
            "src/test/java/resources/outputFromFileWriterTest";
    private static FileWriter fileWriter;

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test(expected = RuntimeException.class)
    public void fileWriter_wrongPath_notOk() {
        fileWriter.write(REPORT, WRONG_PATH);
    }

    @Test
    public void fileWriter_correctPath_ok() throws IOException {
        fileWriter.write(REPORT, COMPARE_PATH);
        List<String> expected = Files.readAllLines(Path.of(COMPARE_PATH));
        List<String> actual = Files.readAllLines(Path.of(CORRECT_PATH));
        Assert.assertEquals(expected, actual);
    }
}
