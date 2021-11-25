package core.service.impl;

import core.service.FileWriterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileWriterImplTest {
    private static FileWriterService writer;
    private static final String VALID_PATH = "src/test/resources/report.csv";
    private static final String NOT_VALID_PATH = "src/test/resources1/outputDiffFile.csv";

    @BeforeClass
    public static void beforeClass() {
        writer = new FileWriterImpl();
    }

    @Test
    public void writeData_validFilePath_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,56";
        writer.writeData(VALID_PATH, expected);
        String actual = readData(VALID_PATH);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeData_notValidPath_notOk() {
        String data = "fruit,quantity" + System.lineSeparator()
                + "apple,56";
        writer.writeData(NOT_VALID_PATH, data);
    }

    private String readData(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException("File doesn't exist");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getPath()))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
            return stringBuilder.toString().trim();
        } catch (IOException e) {
            throw new RuntimeException("Can't read file! " + path, e);
        }
    }
}
