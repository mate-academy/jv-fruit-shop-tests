package core.basesyntax.service;

import core.basesyntax.service.impl.FileWriterImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.Assert.*;

public class FileWriterTest {
    private static final String OUTPUT_TEST_FILE = "src/main/resources/ReportTest.csv";
    private static FileWriter fileWriter;
    private static List<String> expect;
    private List<String> actual;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileWriter = new FileWriterImpl();
        expect = List.of("fruit,quantity", "b,banana,20",
                "b,apple,100");
    }

    @Test
    public void writeToFile_Ok() {
        fileWriter.writeReportToFile(expect, OUTPUT_TEST_FILE);
        try {
            actual = Files.readAllLines(Path.of(OUTPUT_TEST_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file" + OUTPUT_TEST_FILE, e);
        }
        assertEquals(expect, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fileOfPathIsEmpty_NotOk() {
        String pathIncorrect = "";
        fileWriter.writeReportToFile(expect, pathIncorrect);
    }

    @Test(expected = RuntimeException.class)
    public void fileOfPathNull_NotOk() {
        fileWriter.writeReportToFile(expect, null);
    }

    @Test(expected = RuntimeException.class)
    public void infoNull_NotOk() {
        fileWriter.writeReportToFile(null, OUTPUT_TEST_FILE);
    }
}
