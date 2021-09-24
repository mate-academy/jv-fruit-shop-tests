package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FileReaderImplTest {
    public static final String PATH_OK = "src/main/resources/inputData.csv";
    public static final String PATH_EMPTY_FILE = "src/main/resources/empty.csv";
    public static final FileReader reader = new FileReaderImpl();
    private String expected;

    @Test
    public void fileReader_normalData_Ok() {
        expected = "b,banana,20 b,apple,100 s,banana,100 p,banana,13 r,apple,10 p,apple,"
                + "20 p,banana,5 s,banana,50 b,orange,20 b,cherry,1000 s,orange,100 p,cherry,13 ";
        assertEquals("Result must be " + expected + ", but was: " + reader.read(PATH_OK),
                expected, reader.read(PATH_OK));
    }

    @Test(expected = RuntimeException.class)
    public void fileReader_invalidPath_not_Ok() {
        reader.read("helloWorld/helloWorld.csv");
    }

    @Test
    public void fileReader_emptyFile_Ok() {
        expected = "";
        assertEquals("Result must be " + expected + ", but was: " + reader.read(PATH_EMPTY_FILE),
                expected,reader.read(PATH_EMPTY_FILE));
    }
}
