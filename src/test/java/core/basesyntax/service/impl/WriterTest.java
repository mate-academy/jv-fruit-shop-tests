package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileWriterReport;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterTest {
    private static FileWriterReport fileWriter;
    private final String fileName = "src/test/resources/file.csv";

    @BeforeClass
    public static void setUp() {
        fileWriter = new Writer();
    }

//    @Test(expected = RuntimeException.class)
//    public void writeToFile_IncorrectFileName_NotOk() {
//        fileWriter.writeToFile("", "src/test/resources/12*34.txt");
//    }

    @Test
    public void writeToFile_EmptyData_Ok() {
        fileWriter.writeToFile("", fileName);
        try {
            List<String> actualStrings = Files.readAllLines(new File(fileName).toPath());
            assertEquals("You should create empty file for the empty data.",
                    Collections.EMPTY_LIST, actualStrings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void writeToFile_CorrectFile_Ok() {
        String request = "123" + System.lineSeparator() + "456" + System.lineSeparator() + "789";
        fileWriter.writeToFile(request, fileName);
        try {
            List<String> actualStrings = Files.readAllLines(new File(fileName).toPath());
            assertTrue(actualStrings.size() == 3);
            assertEquals("The first row must be 123","123", actualStrings.get(0));
            assertEquals("The second row must be 456", "456", actualStrings.get(1));
            assertEquals("The firth row must be 789", "789", actualStrings.get(2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
