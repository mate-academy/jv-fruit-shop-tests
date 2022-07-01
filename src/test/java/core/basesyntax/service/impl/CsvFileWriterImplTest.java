package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvFileWriterImplTest {

    private static String expected;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        expected = "fruit,quantity\n"
                + "banana,152\n"
                + "apple,90";
    }

    @Test
    public void writeToFile_Ok() {
        String filePath = "src/main/java/core/basesyntax/"
                + "resources/Report.csv";

        StringWriter stringWriter = new StringWriter();
        new CsvFileWriterImpl().writeToFile(filePath, expected);
        String actual;
        try {
            actual = Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from report file " + filePath, e);
        }
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_invalidPath_notOk() {
        String filePath = "";
        thrown.expectMessage("Can't write to file \"" + filePath + "\"");
        new CsvFileWriterImpl().writeToFile(filePath, expected);
    }
}
