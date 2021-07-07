package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Test;

public class FileWriterTest {
    public static final String PATH_FILE_REPORT = "src/main/resources/report.csv";
    public static final String REPORT_IS_NOT_VALID = "fruit_quantity" + System.lineSeparator()
            + "apple90\n"
            + "banana152";
    public static final String REPORT_VALID = "fruit,quantity" + System.lineSeparator()
            + "apple,90" + System.lineSeparator()
            + "banana,152";

    private FileWriter fileWriter;

    @Before
    public void setFileWriter() {
        fileWriter = new FileWriter();
    }

    @Test
    public void fileWriter_successfulWrite_ok() {
        fileWriter.writeToFile(REPORT_VALID, PATH_FILE_REPORT);
        try {
            String actual = String
                    .join(System.lineSeparator(), Files.readAllLines(Path.of(PATH_FILE_REPORT)));
            assertEquals(REPORT_VALID, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = RuntimeException.class)
    public void fileWriter_NullArgs_notOk() {
        fileWriter.writeToFile(null, "");
    }

    @Test(expected = RuntimeException.class)
    public void fileWriter_reportIsNotMatchedRegex() {
        fileWriter.writeToFile(REPORT_IS_NOT_VALID, "");
    }
}
