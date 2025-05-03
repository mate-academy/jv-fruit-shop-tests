package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReadFromFile;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReadFromFileImplTest {
    private ReadFromFile reader;

    @Before
    public void setUp() {
        reader = new ReadFromFileImpl();
    }

    @Test
    public void readFromFile_wrongPath_notOk() {
        String wrongPath = "src/test/java/resources/file123.txt";
        try {
            reader.readFormFile(wrongPath);
            Assert.fail("Expected RunTimeException");
        } catch (RuntimeException e) {
            assertEquals("No such file found!", e.getMessage());
        }
    }

    @Test
    public void readFromFile_nullPath_notOk() {
        try {
            reader.readFormFile(null);
            Assert.fail("Expected RunTimeException");
        } catch (RuntimeException e) {
            assertEquals("No path has been put", e.getMessage());
        }
    }

    @Test
    public void readFromFile_rightPath_ok() {
        List<String> rightList = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        String rightPath = "src/test/java/resources/file.txt";
        List<String> readList = reader.readFormFile(rightPath);
        assertEquals(rightList, readList);
    }
}
