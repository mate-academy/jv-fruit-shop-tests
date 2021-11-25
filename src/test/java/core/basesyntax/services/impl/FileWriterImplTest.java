package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import core.basesyntax.services.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void write_validData_Ok() {
        String filePath = "src/test/resources/output_valid_data.csv";
        String expectedData = "line 1" + System.lineSeparator() + "line 2" + System.lineSeparator()
                + "line 3" + System.lineSeparator() + "line 4";
        fileWriter.write(filePath, expectedData);
        File file = new File(filePath);
        assertTrue("File doesn't exits.", file.exists());
        List<String> data;
        try {
            data = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Unable to open file", e);
        }
        assertEquals(expectedData, String.join(System.lineSeparator(), data));
    }

    @Test
    public void write_emptyData_Ok() {
        String filePath = "src/test/resources/output_valid_data.csv";
        fileWriter.write(filePath, "");
        File file = new File(filePath);
        assertTrue("File doesn't exits.", file.exists());
        String actualData;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            actualData = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Unable to open file", e);
        }
        assertNull(actualData);
    }

    @Test(expected = RuntimeException.class)
    public void write_nullData_NotOk() {
        fileWriter.write("src/test/resources/output_valid_data.csv", null);
    }

    @Test(expected = RuntimeException.class)
    public void write_withNullValues_NotOk() {
        fileWriter.write(null, null);
    }

    @Test(expected = RuntimeException.class)
    public void write_nullPath_NotOk() {
        fileWriter.write(null, "data");
    }

    @Test(expected = RuntimeException.class)
    public void write_emptyPath_NotOk() {
        fileWriter.write("", "data");
    }
}
