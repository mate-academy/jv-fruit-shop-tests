package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportWriterToFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriterToFileImplTest {
    private static final String FILE_VALID = "src/test/resources/TestReport.csv";
    private static final String FILE_NONE_VALID = "src/test/resources/Tes*.csv";
    private static ReportWriterToFile writeToFile;

    @BeforeClass
    public static void beforeClass() {
        writeToFile = new ReportWriterToFileImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writer_notValidNameFile_notOk() {
        String data = "fruit,quantity" + System.lineSeparator() + "apple" + "," + "15";
        writeToFile.writeReportToFile(data, FILE_NONE_VALID);
    }

    @Test
    public void writer_createValidFile_ok() {
        String data = "fruit,quantity" + System.lineSeparator() + "apple" + "," + "15";
        writeToFile.writeReportToFile(data, FILE_VALID);
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("apple" + "," + "15");
        List<String> actual;
        actual = getListFromFile(FILE_VALID);
        assertEquals(expected, actual);
    }

    private List<String> getListFromFile(String file) {
        List<String> actual;
        try {
            actual = Files.readAllLines(Paths.get(file));
        } catch (IOException e) {
            throw new RuntimeException("Cant read file" + file, e);
        }
        return actual;
    }
}
