package core.basesyntax.service;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteToFileImplTest {
    private static WriteToFile writeToFile;
    private static ReadFile readFile;
    private String toWriteFile;
    private String toWriteData;

    @BeforeClass
    public static void setUp() {
        writeToFile = new WriteToFileImpl();
        readFile = new ReadFileImpl();
    }

    @Test (expected = ValidationException.class)
    public void writeToWrongPath_NotOk() {
        toWriteData = "test of file";
        toWriteFile = "";
        writeToFile.writeToFile(toWriteFile, toWriteData);
    }

    @Test
    public void writeToFileCorrectPath_Ok() {
        toWriteData = "test of file";
        toWriteFile = "src/main/resources/testfile.csv";
        writeToFile.writeToFile(toWriteFile, toWriteData);
        List<String> list = readFile.readFromFile(toWriteFile);
        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(0), toWriteData);
    }
}
