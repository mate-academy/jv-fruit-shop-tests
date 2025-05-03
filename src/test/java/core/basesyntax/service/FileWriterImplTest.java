package core.basesyntax.service;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static FileReader fileReader;
    private String toWriteFile;
    private String toWriteData;

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test (expected = ValidationException.class)
    public void writeToWrongPath_NotOk() {
        toWriteData = "test of file";
        toWriteFile = "";
        fileWriter.writeToFile(toWriteFile, toWriteData);
    }

    @Test
    public void writeToFileCorrectPath_Ok() {
        toWriteData = "test of file";
        toWriteFile = "src/main/resources/testfile.csv";
        fileWriter.writeToFile(toWriteFile, toWriteData);
        List<String> list = fileReader.readFromFile(toWriteFile);
        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(0), toWriteData);
    }
}
