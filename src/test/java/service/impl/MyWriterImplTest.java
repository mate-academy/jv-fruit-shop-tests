package service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import service.MyWriter;

public class MyWriterImplTest {
    private static MyWriter myWriter;

    @BeforeClass
    public static void beforeClass() {
        myWriter = new MyWriterImpl();
    }

    @Test
    public void write_validData_Ok() {
        String filePath = "src/test/resources/output_valid_data.csv";
        List<String> expectedList = List.of(
                "Write this1",
                "Write this2",
                "And this too"
        );
        myWriter.writeToFile(expectedList, filePath);
        File file = new File(filePath);
        assertTrue("File doesn't exits.", file.exists());
        List<String> data;
        try {
            data = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
        assertEquals(expectedList, data);
    }

    @Test(expected = RuntimeException.class)
    public void write_invalid_file_path_Ok() {
        String filePath = "";
        List<String> expectedList = List.of(
                "Write this1",
                "Write this2",
                "And this too"
        );
        myWriter.writeToFile(expectedList, filePath);
    }

    @Test(expected = RuntimeException.class)
    public void write_nullData_notOk() {
        myWriter.writeToFile(null, "src/test/resources/output_valid_data.csv");
    }

    @Test(expected = RuntimeException.class)
    public void write_nullPath_notOk() {
        List<String> expectedList = List.of(
                "Write this1",
                "Write this2",
                "And this too"
        );
        myWriter.writeToFile(expectedList, null);
    }
}
