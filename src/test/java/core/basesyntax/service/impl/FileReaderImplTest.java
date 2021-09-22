package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String sourceFile = "src/main/java/resources/filetest";
    private FileReaderImpl fileService = new FileReaderImpl();
    private List<String> expected = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
    }

    @Test(expected = NullPointerException.class)
    public void readFile_Null_notOk() {
        fileService.read(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_incorrectPath_NotOk() {
        fileService.read("src/main/java/resourcesee/filetest");
    }

    @Test
    public void readFile_validFileName_Ok() {
        assertEquals(expected, fileService.read(sourceFile));
    }
}
