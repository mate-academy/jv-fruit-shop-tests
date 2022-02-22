package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitDto;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteDataToFileTest {
    private static WriteDataToFile writer;
    private static ReadService readService = new ReadService();
    private static final String toFileName = "src/test/resources/currentReportText.csv";
    private final String currentReportText = "fruit,quantity" + System.lineSeparator()
            + "apple,150" + System.lineSeparator() + "banana,100";

    @BeforeClass
    public static void beforeClass() {
        writer = new WriteDataToFile();
    }

    @Test
    public void writer_data_ok() throws IOException {
        writer.writeToFile(currentReportText, toFileName);
        String expected = currentReportText;
        String actual = Files.readString(Paths.get(toFileName));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writer_dataWithEmptyReportText_notOk() throws IOException {
        writer.writeToFile("", toFileName);
        final List<FruitDto> data = readService.readData(toFileName);
        final int excepted = 0;
        final int actual = data.size();
        assertEquals(excepted, actual);
    }

    @AfterClass
    public static void deleteData() {
        try {
            FileWriter fstream1 = new FileWriter(toFileName);
            BufferedWriter out1 = new BufferedWriter(fstream1);
            out1.write("");
            out1.close();
        } catch (IOException e) {
            throw new RuntimeException("Delete text", e);
        }

    }

}
