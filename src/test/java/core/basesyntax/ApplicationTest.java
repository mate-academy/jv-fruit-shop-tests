package core.basesyntax;

import core.basesyntax.service.ReaderFromFile;
import core.basesyntax.service.impl.ReaderFromFileImpl;
import java.io.File;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ApplicationTest {
    private static ReaderFromFile reader;
    private static List<String> actual;
    private static File file;
    private static String pathToResultFile;
    private static String expectedTitle;
    private static String expectedFirstLine;
    private static String expectedSecondLine;

    @BeforeClass
    public static void beforeClass() {
        Application.main(new String[]{});
        reader = new ReaderFromFileImpl();
        pathToResultFile = "src/main/resources/result.csv";
        expectedTitle = "fruit,quantity";
        expectedFirstLine = "banana,152";
        expectedSecondLine = "apple,90";
    }

    @Test
    public void correctWorkingApplication_ok() {
        actual = reader.getData(pathToResultFile);
        Assert.assertEquals(expectedTitle, actual.get(0));
        Assert.assertEquals(expectedFirstLine, actual.get(1));
        Assert.assertEquals(expectedSecondLine, actual.get(2));
    }

    @AfterClass
    public static void afterClass() {
        file = new File(pathToResultFile);
        file.delete();
    }
}
