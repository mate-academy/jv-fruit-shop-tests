package core.basesyntax.service.writereadcsv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String FILE_TO_WRITE =
            "src/test/java/core/basesyntax/resources/outputdate/report.csv";
    private FileWriter fileWriter;
    private final String report = "fruit,quantity"
            + System.lineSeparator() + "banana,152"
            + System.lineSeparator() + "apple,90";

    @Before
    public void setUp() throws Exception {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeToFileCsv_Ok() {
        fileWriter.writeToFileCsv(report,FILE_TO_WRITE);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(FILE_TO_WRITE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> expected = Arrays.asList("fruit,quantity",
                "banana,152","apple,90");
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = NullPointerException.class)
    public void nullFileName_NotOk() {
        fileWriter.writeToFileCsv(report,null);
    }

    @Test(expected = NullPointerException.class)
    public void nullReport_NotOk() {
        fileWriter.writeToFileCsv(null,FILE_TO_WRITE);
    }

    @Test(expected = NullPointerException.class)
    public void nullReportAndFileName_NotOk() {
        fileWriter.writeToFileCsv(null,null);
    }
}
