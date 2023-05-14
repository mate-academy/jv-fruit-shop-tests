package core.basesyntax.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvFileReaderTest {
    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;
    private static final int THIRD_INDEX = 2;
    private static CsvFileReader reader;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void incorrectFileAddress_NotOk() {
        reader = new CsvFileReaderImpl("test/resources/emptyInputDataFile.csv");
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Can't find file: ");
        reader.getDataFromFile();
    }

    @Test
    public void emptyInputFile_NotOk() {
        reader = new CsvFileReaderImpl("src/test/resources/emptyInputDataFile.csv");
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Not enough data in file or file is empty ");
        reader.getDataFromFile();
    }

    @Test
    public void headLineOnlyInFile_NotOk() {
        reader = new CsvFileReaderImpl("src/test/resources/headLineOnlyInFile.csv");
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Not enough data in file or file is empty ");
        reader.getDataFromFile();
    }

    @Test
    public void create_ListOfArrays_FromDataFile() {
        reader = new CsvFileReaderImpl("src/test/resources/storeInputDataTestFile.csv");
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"b", "banana", "20"});
        data.add(new String[]{"b", "apple", "100"});
        data.add(new String[]{"s", "banana", "100"});
        List<String[]> actual = reader.getDataFromFile();
        assertEquals(3, actual.size());
        assertArrayEquals(data.get(FIRST_INDEX), actual.get(FIRST_INDEX));
        assertArrayEquals(data.get(SECOND_INDEX), actual.get(SECOND_INDEX));
        assertArrayEquals(data.get(THIRD_INDEX), actual.get(THIRD_INDEX));
    }
}
