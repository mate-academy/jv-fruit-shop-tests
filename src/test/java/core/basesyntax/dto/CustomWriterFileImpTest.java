package core.basesyntax.dto;

import core.basesyntax.imp.CustomFileReadImp;
import core.basesyntax.service.CustomFileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CustomWriterFileImpTest {
    private static final String TEXT = "Hello mates!";
    private static final String RIGHT_PATH = "src/test/test.csv";
    private static final String WRONG_PATH = "wrong/test/test.csv";
    private static CustomWriterFile writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new CustomWriterFileImp();
    }

    @Test
    public void checkWriteFile_ok() {
        CustomFileReader reader = new CustomFileReadImp();
        List<String> expected = new ArrayList<>();

        expected.add(TEXT);
        writer.writeFile(RIGHT_PATH, TEXT.trim());
        List<String> actual = reader.readFromFile(RIGHT_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void checkWrongFile_notOk() {
        writer.writeFile(WRONG_PATH, TEXT);
    }
}
