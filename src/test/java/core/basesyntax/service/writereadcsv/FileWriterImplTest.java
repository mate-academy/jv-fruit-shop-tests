package core.basesyntax.service.writereadcsv;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String FILE_TO_WRITE =
            "src/test/java/core/basesyntax/resources/outputdate/report.csv";
    private FileWriter fileWriter;
    private final String report = "fruit,quantity"
            + System.lineSeparator() + "banana,152"
            + System.lineSeparator() + "apple,90";

    public FileWriterImplTest() {
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
        Assert.assertEquals(expected.toString(),actual.toString());
    }

    @Test
    public void nullFileName_NotOk() {
        try {
            fileWriter.writeToFileCsv(report,null);
        } catch (NullPointerException e) {
            return;
        }
        fail("File name can't be null. NullPointerException");
    }

    @Test
    public void nullReport_NotOk() {
        try {
            fileWriter.writeToFileCsv(null,FILE_TO_WRITE);
        } catch (NullPointerException e) {
            return;
        }
        fail("Report name can't be null. NullPointerException");
    }

    @Test
    public void nullReportAndFileName_NotOk() {
        try {
            fileWriter.writeToFileCsv(null,FILE_TO_WRITE);
        } catch (NullPointerException e) {
            return;
        }
        fail("Report name can't be null. NullPointerException");
    }

    @Test
    public void inValidFileName_NotOk() {
        String inValidFileName = "*???????";
        try {
            fileWriter.writeToFileCsv(report,inValidFileName);
        } catch (Exception e) {
            return;
        }
        fail("Can't write data to the file, invalid file name "
                + "or replace report and file name");
    }

    @Test
    public void changePlaceReportFile_NotOk() {
        try {
            fileWriter.writeToFileCsv(FILE_TO_WRITE,report);
        } catch (RuntimeException e) {
            return;
        }
        fail("Can't write data to the file, invalid file name "
                + "or replace report and file name");
    }
}
