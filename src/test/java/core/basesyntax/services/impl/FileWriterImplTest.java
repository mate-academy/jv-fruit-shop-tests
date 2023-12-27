package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.services.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String OUTPUT_FILEPATH
            = "src/main/java/core/basesyntax/resources/output.csv";
    private static final String FALSE_OUTPUT_FILEPATH
            = "src/main/java/core/baseax/resources/output.csv";
    private FileWriter fileWriter;

    @Before
    public void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeToFile_Ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        boolean actual = fileWriter.writeToFile(OUTPUT_FILEPATH, report);
        assertTrue(actual);
    }

    @Test
    public void writeToFile_CheckRightWrittedData_Ok() throws IOException {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        fileWriter.writeToFile(OUTPUT_FILEPATH, report);
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,20");
        expected.add("apple,100");
        List<String> actual = Files.readAllLines(Path.of(OUTPUT_FILEPATH));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_FalseOutputPath_ThrowExc_NotOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        fileWriter.writeToFile(FALSE_OUTPUT_FILEPATH, report);
    }
}
