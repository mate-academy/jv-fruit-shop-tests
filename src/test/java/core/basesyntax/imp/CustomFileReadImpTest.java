package core.basesyntax.imp;

import core.basesyntax.service.CustomFileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CustomFileReadImpTest {
    private static final String PATH_FILE_WRONG = "src/test/wrong.file.csv";
    private static final String PATH_FILE_RIGHT = "src/test/write.read.one.line.csv";
    private static CustomFileReader reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new CustomFileReadImp();
    }

    @Test(expected = RuntimeException.class)
    public void wrongPath_noOk() {
        reader.readFromFile(PATH_FILE_WRONG);
    }

    @Test
    public void rightPath_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("Hello mates!");
        Assert.assertEquals(expected,reader.readFromFile(PATH_FILE_RIGHT));
    }
}
