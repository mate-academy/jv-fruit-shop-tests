package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Test;

public class FileWriterTest {
    public static final String PATH_FILE_REPORT = "src/main/resources/report.csv";
    public static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "apple,90" + System.lineSeparator()
            + "banana,152";
    private FileWriter fileWriter;

    @Before
    public void setFileWriter() {
        fileWriter = new FileWriter();
    }

    @Test
    public void fileWriter_successfulRead_ok() {
        fileWriter.writeToFile(REPORT, PATH_FILE_REPORT);
        try {
            assertEquals(REPORT, String.join(System.lineSeparator(),
                    Files.readAllLines(Path.of(PATH_FILE_REPORT))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
