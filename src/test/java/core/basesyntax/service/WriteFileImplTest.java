package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class WriteFileImplTest {
    private static WriteFile writeFile;
    private static Map<String,Integer> inputMap;
    private static final String reportPath = "src/main/resources/report.csv";
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
        assertTrue(writeFile.writeWithMapToFile(inputMap,reportPath));
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_incorrectPath_notOk() {
        assertTrue(writeFile.writeWithMapToFile(inputMap,"helloWorld/helloWord.csv"));
    }
}
