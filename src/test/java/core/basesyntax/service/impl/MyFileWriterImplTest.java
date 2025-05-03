package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.MyFileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MyFileWriterImplTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    private final MyFileWriter myFileWriter = new MyFileWriterImpl();

    @Test
    public void writeReportToFile_valid_ok() {
        myFileWriter.writeToFile("src" + File.separator + "main" + File.separator
                        + "resources" + File.separator + "report_test.csv",
                List.of("fruit,quantity" + System.lineSeparator() + "banana,100"
                        + System.lineSeparator() + "apple,90" + System.lineSeparator()));
        List<String> expected = List.of("fruit,quantity", "banana,100", "apple,90");
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of("src" + File.separator
                    + "main" + File.separator + "resources" + File.separator + "report_test.csv"));
        } catch (IOException e) {
            throw new RuntimeException("Can not read the file report_test.csv", e);
        }
        assertEquals(expected, actual);
    }

    @Test
    public void writeInfo_nullPath_notOk() {
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.reportMissingExceptionWithMessage("Should get exception for null file path");
        myFileWriter.writeToFile(null,
                List.of("fruit,quantity" + System.lineSeparator() + "banana,100"
                + System.lineSeparator() + "apple,90" + System.lineSeparator()));
    }

    @Test
    public void writeInfo_emptyPath_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.reportMissingExceptionWithMessage("Should get exception for empty file path");
        myFileWriter.writeToFile("",
                List.of("fruit,quantity" + System.lineSeparator() + "banana,100"
                        + System.lineSeparator() + "apple,90" + System.lineSeparator()));
    }

    @Test
    public void writeInfo_nonExistingFile_ok() {
        myFileWriter.writeToFile("src" + File.separator
                + "main" + File.separator + "resources" + File.separator + "nonExisting.csv",
                List.of("fruit,quantity" + System.lineSeparator() + "banana,100"
                        + System.lineSeparator() + "apple,90" + System.lineSeparator()));
    }
}
