package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static WriterService writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterServiceImpl();
    }

    @Test
    public void writeData_validFilePath_ok() {
        String path = "src/test/resources/outputFile.csv";
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,56";
        String actual = readData(path);
        writer.writeData(path, expected);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeData_notValidPath_notOk() {
        String path = "src/test/resources1/outputFile1.csv";
        String data = "fruit,quantity" + System.lineSeparator()
                + "apple,56";
        writer.writeData(path, data);
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
