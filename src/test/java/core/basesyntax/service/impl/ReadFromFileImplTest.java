package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.service.ReadFromFile;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Test;

public class ReadFromFileImplTest {
    private final String validPathFrom = "src/main/resources/resources.csv";
    private final String invalidPathFrom = "src/main/resources/r.csv";

    private final String fileData = "type,fruit,quantity" + System.lineSeparator()
            + "b,banana,20" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,10" + System.lineSeparator()
            + "p,banana,13" + System.lineSeparator()
            + "r,apple,10" + System.lineSeparator()
            + "p,apple,20" + System.lineSeparator()
            + "p,banana,5" + System.lineSeparator()
            + "s,banana,10";
    private ReadFromFile readFromFile;

    @Before
    public void setUp() {
        readFromFile = new ReadFromFileImpl();
    }

    @Test
    public void readFromFileTest_Ok() {
        String fileActual = readFromFile.readFile(Path.of(validPathFrom));
        assertEquals(fileData, fileActual);
    }

    @Test
    public void readFromFileTest_invalidData() {
        String fileActual = readFromFile.readFile(Path.of(validPathFrom)) + "this is error!";
        assertNotEquals(fileData, fileActual);
    }

    @Test (expected = RuntimeException.class)
    public void readFile_Fail() {
        readFromFile.readFile(Path.of(invalidPathFrom));
    }
}
