package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileReaderImplTest {
    private static FileReader fileReader;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void initializeFields() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_WhitespaceInsidePath_throwException() {
        String inputFilePath = "resources/ activities.csv";
        expectedException.expect(RuntimeException.class);
        fileReader.read(inputFilePath);
    }

    @Test
    public void read_WhitespaceInTheEndOfPath_throwException() {
        String inputFilePath = "resources/activities.csv ";
        expectedException.expect(RuntimeException.class);
        fileReader.read(inputFilePath);
    }

    @Test
    public void read_Non_existentPath_throwException() {
        String inputFilePath = "resources/actiities.csv ";
        expectedException.expect(RuntimeException.class);
        fileReader.read(inputFilePath);
    }

    @Test
    public void read_PathIsExist_OK() {
        String inputFilePath = "resources/activities.csv";
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
