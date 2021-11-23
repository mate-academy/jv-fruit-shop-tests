package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class WriteFileImplTest {
    private static final String REPORT_PATH = "src/main/resources/report.csv";
    private static WriteFile writeFile;
    private static Map<String,Integer> inputMap;
    private Object expected;

    @Before
    public void setUp() {
        writeFile = new WriteFileImpl();
        inputMap = new HashMap<>();
        inputMap.put("orange", 50);
        inputMap.put("cherry", 1545);
    }

    @Test
    public void writeFile_normalData_Ok() {
        writeFile.writeWithMapToFile(inputMap, REPORT_PATH);
        List<String> result;
        try {
            result = Files.readAllLines(Path.of(REPORT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read this file " + REPORT_PATH, e);
        }
        String[]firstRecord = result.get(0).split(",");
        String[]secondRecord = result.get(1).split(",");
        assertEquals("Wrong entry, expected orange, but was " + firstRecord[0],
                "orange", firstRecord[0]);
        assertEquals("Wrong entry, expected 50, but was " + firstRecord[1],
                "50", firstRecord[1]);
        assertEquals("Wrong entry, expected cherry, but was " + secondRecord[0],
                "cherry", secondRecord[0]);
        assertEquals("Wrong entry, expected 1545, but was " + secondRecord[1],
                "1545", secondRecord[1]);
        assertEquals("Expected number of records 2, but was ," + result.size(),
                2, result.size());
        assertTrue("Failed write data to the file",
                writeFile.writeWithMapToFile(inputMap, REPORT_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_incorrectPath_notOk() {
        writeFile.writeWithMapToFile(inputMap,"helloWorld/helloWord.csv");
    }
}
