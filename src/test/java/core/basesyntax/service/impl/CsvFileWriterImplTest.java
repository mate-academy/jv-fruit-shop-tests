package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvFileWriterImplTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final FileWriter csvFileWriter = new CsvFileWriterImpl();

    @Test
    public void writeToFile_Ok() {
        String expected = "fruit,quantity\n"
                + "banana,152\n"
                + "apple,90";

        String filePath = "src/main/java/core/basesyntax/"
                + "resources/Report.csv";

        csvFileWriter.writeToFile(filePath, expected);
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
        csvFileWriter.writeToFile(filePath, "writeToFile_invalidPath_notOk test");
    }
}
