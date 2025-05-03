package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String OUTPUT_FILE = "src/main/resources/output.csv";
    private static FileWriter fileWriter;
    private static String report;
    private static List<String> expected;
    private static List<String> actual;

    @BeforeClass
    public static void beforeClass() {
        expected = new ArrayList<>();
        actual = new ArrayList<>();
        fileWriter = new FileWriterImpl();
        report = "fruit,quantity" + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator();
    }

    @Test
    public void write_correctData_ok() {
        fileWriter.write(report, OUTPUT_FILE);
        try {
            actual = Files.readAllLines(Path.of(OUTPUT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + OUTPUT_FILE, e);
        }
        expected.add("fruit,quantity");
        expected.add("banana,152");
        expected.add("apple,90");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToNullFilePath_notOk() {
        fileWriter.write(report, null);
    }

    @After
    public void tearDown() throws Exception {
        expected.clear();
    }
}
