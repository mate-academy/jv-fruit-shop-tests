package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvFileWriterImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void writeToFile_Ok() {
        String output = "src/main/java/core/basesyntax/"
                + "resources/Report.csv";
        String expected = "fruit,quantity\n"
                + "banana,152\n"
                + "apple,90";
        StringWriter stringWriter = new StringWriter();
        new CsvFileWriterImpl().writeToFile(output, expected);
        String actual;
        try {
            actual = Files.readString(Path.of(output));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from report file", e);
        }
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_notOk() {
        String filePath = "";
        String expected = "fruit,quantity\n"
                + "banana,152\n"
                + "apple,90";
        thrown.expectMessage("Can't write to file \"" + filePath + "\"");
        new CsvFileWriterImpl().writeToFile(filePath, expected);
    }
}
