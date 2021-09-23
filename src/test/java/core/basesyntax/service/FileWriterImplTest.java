package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String FILE_PATH = "src/test/resources/fruitReport.csv";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152"
            + System.lineSeparator()
            + "apple,90"
            + System.lineSeparator();
    private static FileWriter fileWriter;
    private List<String> actual;
    private String expected;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeToFile_ok() throws IOException {
        fileWriter.writeToFile(REPORT, FILE_PATH);
        try {
            actual = Files.readAllLines(Path.of(String.valueOf(new File(FILE_PATH))));
        } catch (IOException e) {
            fail("Can't read data from file");
        }
        expected = "fruit,quantity";
        assertEquals(actual.get(0), expected);
    }

    @Test(expected = RuntimeException.class)
    public void writeToNullFile_notOk() {
        fileWriter.writeToFile(REPORT, null);
    }
}
