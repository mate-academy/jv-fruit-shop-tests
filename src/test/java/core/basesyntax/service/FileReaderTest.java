package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void fileReader_correctFile_ok() {
        String fileName = "src/test/resources/FileReaderTest/correctWork.cvs";
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = fileReader.readFromFile(fileName);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void fileReader_fileNameNull_notOk() {
        fileReader.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void fileReader_incorrectFileName_notOk() {
        String fileName = "Hello";
        fileReader.readFromFile(fileName);
    }
}
