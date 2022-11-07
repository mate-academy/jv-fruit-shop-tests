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
    private static final String METHOD_WRITE_PATH =
            "src/test/java/resources/outputFromFileWriterTest";
    private static FileWriter fileWriter;

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToWrongPath_NotOk() {
        fileWriter.write(REPORT, WRONG_PATH);
    }

    @Test
    public void writeToCorrectPath_Ok() throws IOException {
        fileWriter.write(REPORT, METHOD_WRITE_PATH);
        List<String> expected = Files.readAllLines(Path.of(COMPARE_PATH));
        List<String> actual = Files.readAllLines(Path.of(METHOD_WRITE_PATH));
        Assert.assertEquals(expected, actual);
    }
}
