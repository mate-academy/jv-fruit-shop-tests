package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;

    @BeforeClass
    public static void initializeFields() {
        fileReader = new FileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void read_whitespaceInsidePath_throwException() {
        String inputFilePath = "src/test/resources/ activities.csv";
        fileReader.read(inputFilePath);
    }

    @Test(expected = RuntimeException.class)
    public void read_whitespaceInTheEndOfPath_throwException() {
        String inputFilePath = "src/test/resources/activities.csv ";
        fileReader.read(inputFilePath);
    }

    @Test(expected = RuntimeException.class)
    public void read_non_existentPath_throwException() {
        String inputFilePath = "src/test/resources/actiities.csv";
        fileReader.read(inputFilePath);
    }

    @Test
    public void read_pathIsExist_OK() {
        String inputFilePath = "src/test/resources/activities.csv";
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReader.read(inputFilePath);
        Assert.assertEquals(expected, actual);
    }
}
