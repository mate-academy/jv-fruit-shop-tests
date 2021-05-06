package core.basesyntax.fruitshop.util;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteServiceImplTest {
    private static String filePath;
    private static String output;
    private static WriteService writeService;

    @BeforeClass
    public static void beforeClass() {
        writeService = new WriteServiceImpl();
    }

    @Test
    public void writeToFile_BasicOutput_isOk() {
        output = "fruit,quantity\r\nbanana,10\r\napple,90\r\n";
        filePath = "src/test/resources/test_output_basic.csv";
        writeService.writeToFile(output, filePath);
        String actual = getTextFromFile(filePath);
        Assert.assertEquals(output, actual);
    }

    @Test
    public void writeToFile_EmptyOutput_isOk() {
        output = "";
        filePath = "src/test/resources/test_output_basic.csv";
        writeService.writeToFile(output, filePath);
        String actual = getTextFromFile(filePath);
        Assert.assertEquals(output, actual);
    }

    private String getTextFromFile(String filePath) {
        List<String> outputList = new ReadServiceImpl().readFromFile(filePath);
        StringBuilder result = new StringBuilder();
        for (String line : outputList) {
            result.append(line).append(System.lineSeparator());
        }
        return result.toString();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_IncorrectFilePathException_isThrown() {
        filePath = "incorrect_directory/test_output.csv";
        writeService.writeToFile(output, filePath);
    }
}
