package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvFileReaderImplTest {
    private static FileReader csvFileReader;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        csvFileReader = new CsvFileReaderImpl();
    }

    @Test
    public void readFromFile_Ok() {
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
        String filePath = "src/main/java/core/basesyntax/resources/Input.csv";
        List<String> actual = csvFileReader.readFromFile(filePath);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_invalidPath_notOk() {
        String filePath = "invalidFilePath@ukrnet.ua";
        expectedException.expectMessage("Can't read from file \"" + filePath + "\"");
        csvFileReader.readFromFile(filePath);
    }

    @Test
    public void readFromFile_emptyPath_notOk() {
        String filePath = "";
        expectedException.expectMessage("Can't read from file \"" + filePath + "\"");
        csvFileReader.readFromFile(filePath);
    }
}
