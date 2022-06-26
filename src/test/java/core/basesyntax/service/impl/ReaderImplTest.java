package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.Reader;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static Reader reader;

    @BeforeClass
    public static void beforeAllTestMethods() {
        reader = new ReaderImpl();
    }

    @Test
    public void convertFromFileToList_validPath_ok() {
        String validPath = "src/test/java/resources/ValidFile.csv";
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,100");
        expected.add("s,banana,50");
        expected.add("p,banana,14");
        expected.add("r,banana,5");
        expected.add("");
        List<String> actual = reader.convertFromFileToList(validPath);

        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void convertFromFileToList_invalidPath_ok() {
        String invalidPath = "src/test/java/resources/NonExistingFile.csv";

        reader.convertFromFileToList(invalidPath);
    }
}
