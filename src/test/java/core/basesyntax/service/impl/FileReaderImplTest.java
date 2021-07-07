package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void fileReader_usualState_ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13");
        List<String> actual = fileReader.read("src/test/resources/input.csv");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fileReader_invalidPath_notOk() {
        fileReader.read("dfdfsfd");
    }

    @Test
    public void fileReader_emptyFile_ok() {
        List<String> actual = fileReader.read("src/test/resources/emptyInput.csv");
        Assert.assertEquals(Collections.emptyList(), actual);
    }
}
