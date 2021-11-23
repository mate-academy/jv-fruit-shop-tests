package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderCsvImplTest {
    private static Reader fileReader;
    private static final String INPUT_FILE_PATH = "src/test/java/resources/input_database.csv";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fileReader = new ReaderCsvImpl();
    }

    @Test
    public void readFile_validFilePath_Ok() {
        List<String> expected =
                List.of("type,fruit,quantity",
                        "b,banana,20",
                        "b,apple,100",
                        "s,banana,100",
                        "s,apple,13",
                        "r,banana,10",
                        "r,apple,20",
                        "p,banana,5",
                        "p,apple,50");
        List<String> actual = fileReader.readFile(INPUT_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFile_emptyFile_Ok() {
        List<String> expected = new ArrayList<>();
        String pathToEmptyFile = "src/test/java/resources/emptyFile.csv";
        List<String> actual = fileReader.readFile(pathToEmptyFile);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFile_invalidFilePath_notOk() {
        String wrongInputFilePath = INPUT_FILE_PATH + "1";
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Can't get data from file " + wrongInputFilePath);
        fileReader.readFile(wrongInputFilePath);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_nullFilePath_notOk() {
        String wrongInputFilePath = null;
        fileReader.readFile(wrongInputFilePath);
    }

    @Test
    public void readFile_noCharactersFilePath_notOk() {
        String wrongInputFilePath = "";
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Can't get data from file " + wrongInputFilePath);
        fileReader.readFile(wrongInputFilePath);
    }
}
