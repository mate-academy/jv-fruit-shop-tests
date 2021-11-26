package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.reader.MyReaderImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class MyReaderTest {
    private static MyReaderImpl myReader;

    @BeforeClass
    public static void beforeClass() {
        myReader = new MyReaderImpl();
    }

    @Test
    public void readFromFile_correctFile_ok() {
        String fileName = "src/test/resourcesTest/inputTestFile.cvs";
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual = myReader.readFromFile(fileName);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void myReader_fileNameIsNull_notOk() {
        myReader.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void myReader_incorrectFileName_notOk() {
        String fileName = "FileName";
        myReader.readFromFile(fileName);
    }
}
