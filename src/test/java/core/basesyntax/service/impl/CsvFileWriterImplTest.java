package core.basesyntax.service.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class CsvFileWriterImplTest {


    @Test
    public void writeToFile_Ok() {
        String OUTPUT_FILE_PATH = "src/main/java/core/basesyntax/"
                + "resources/Report.csv";
        String expected = "fruit,quantity\n" +
                "banana,152\n" +
                "apple,90";
        StringWriter stringWriter = new StringWriter();
        new CsvFileWriterImpl().writeToFile(OUTPUT_FILE_PATH, expected);
        String actual;
        try {
            actual = Files.readString(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from report file", e);
        }
        assertEquals(expected, actual);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void writeToFile_notOk(){
        String filePath = "";
        String expected = "fruit,quantity\n" +
                "banana,152\n" +
                "apple,90";
        thrown.expectMessage("Can't write to file \"" + filePath + "\"");
        new CsvFileWriterImpl().writeToFile(filePath, expected);
    }

}