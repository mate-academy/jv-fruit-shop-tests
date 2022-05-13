package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_fileWithCorrectInput_Ok() {
        List<String> actual = fileReader.read("src/test/resources/TestInput.csv");
        int expected = 7;
        assertEquals("Size not equals expected size!", actual.size(), expected);
    }

    @Test(expected = RuntimeException.class)
    public void read_wrongInputPath_NotOk() {
        fileReader.read("src/test2/resources/TestInput.csv");
    }
}
