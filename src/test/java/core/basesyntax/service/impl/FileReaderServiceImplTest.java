package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String FILE_INPUT = "src/main/resources/dataInputTest.csv";
    private static FileReaderService readData;

    @BeforeClass
    public static void setUp() {
        readData = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_generaltest_Ok() {
        File file = new File(FILE_INPUT);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String value = reader.readLine();
            while (value != null) {
                builder.append(value).append(System.lineSeparator());
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file" + FILE_INPUT, e);
        }
        String expected = builder.toString();
        FileReaderService readData = new FileReaderServiceImpl();
        String result = readData.readFromFile(FILE_INPUT);
        assertEquals(expected, result);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_PathNull_notOk() {
        readData.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_wrongPath_Ok() {
        readData.readFromFile("abcdea");
    }

    @AfterClass
    public static void afterClass() {
        try {
            new FileWriter(FILE_INPUT, false).close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
