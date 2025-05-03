package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReadData;
import core.basesyntax.service.WriteData;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriteDataImplTest {

    private static final String FILE_PATH = "src/test/resources/report.csv";

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String report = "fruit,quantity"
            + System.lineSeparator()
            + "banana,500"
            + System.lineSeparator()
            + "apple,18";
    private WriteData writeData = new WriteDataImpl();
    private ReadData readData = new ReadDataImpl();

    @Test
    public void writeDataToFile_Ok() {
        String exceptedReport = "fruit,quantity"
                + System.lineSeparator()
                + "banana,500"
                + System.lineSeparator()
                + "apple,18";
        writeData.writeToFile(report, FILE_PATH);
        String actualReport = null;
        try {
            actualReport = Files.readAllLines(Paths.get(FILE_PATH))
                    .stream()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file");
        }
        assertEquals(exceptedReport,actualReport);
    }

    @Test(expected = RuntimeException.class)
    public void writeDataToFile_null_notOk() {
        writeData.writeToFile(null,FILE_PATH);
    }

    @Test
    public void writeDataToFile_NullPath_notOk() {
        String emptyReport = "";
        String emptyPath = "";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Failed to write file: " + emptyPath
                + " to file: " + emptyPath);
        writeData.writeToFile(emptyReport, emptyPath);

    }
}
