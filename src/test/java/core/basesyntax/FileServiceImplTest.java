package core.basesyntax;

import core.basesyntax.service.FileService;
import core.basesyntax.service.FileServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileServiceImplTest {
    private static final FileService readFromFileTest = new FileServiceImpl();
    private String inputPath;
    private String outputPath;

    @Test
    public void readFromFile_ValidInput_oK() {
        inputPath = "src/main/java/core/basesyntax/resources/inputTest.csv";
        List<String> testValue = new ArrayList<>();
        testValue.add("type,fruit,quantity");
        testValue.add("b,banana,20");
        testValue.add("b,apple,100");
        List<String> strings = readFromFileTest.readFromFile(inputPath);
        Assert.assertEquals(strings, testValue);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_InvalidInput_notOk() {
        inputPath = "src/main/java/core/basesyntax/resources/inputTest2.csv";
        List<String> strings = readFromFileTest.readFromFile(inputPath);
    }

    @Test
    public void writeToFile_ValidInput_notOK() {
        outputPath = "src/main/java/core/basesyntax/resources/outputTest.csv";
        String testValue = "type,fruit,quantity";
        readFromFileTest.writeToFile(outputPath, testValue);
        Assert.assertTrue(readFromFileTest.readFromFile(outputPath).contains(testValue));
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_InvalidInput_notOk() {
        outputPath = null;
        String testValue = "type,fruit,quantity";
        readFromFileTest.writeToFile(outputPath, testValue);
    }
}
