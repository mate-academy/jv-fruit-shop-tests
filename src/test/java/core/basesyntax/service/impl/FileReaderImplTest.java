package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String SOURCE_FILE = "src/main/java/resources/filetest";
    private final FileReaderImpl fileReader = new FileReaderImpl();

    @Test
    public void readFile_incorrectPath_NotOk() {
        try {
            fileReader.read("src/main/java/resourcesee/filetest");
        } catch (RuntimeException e) {
            Assert.assertEquals("Unable to read data from file "
                            + "src/main/java/resourcesee/filetest",
                    e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void readFile_validFileName_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        assertEquals(expected, fileReader.read(SOURCE_FILE));
    }
}
