package core.basesyntax.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final String fileInput = "src/main/resources/dataInputTest.csv";
    private FileReaderService readData;

    @BeforeEach
    void setUp() {
        readData = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFileGeneralTest() {
        File file = new File(fileInput);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String value = reader.readLine();
            while (value != null) {
                builder.append(value).append(System.lineSeparator());
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to open file " + fileInput, e);
        }
        String expected = builder.toString();
        FileReaderService readData = new FileReaderServiceImpl();
        String result = readData.readFromFile(fileInput);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void readFromFile_PathNull_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                readData.readFromFile(null)
        );
    }

    @Test
    public void wrongPath() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            readData.readFromFile("abcdea");
        });
    }

    @AfterClass
    public static void afterClass() {
        try {
            new FileWriter(fileInput, false).close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
