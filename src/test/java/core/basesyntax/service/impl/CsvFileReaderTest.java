package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileReaderReport;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvFileReaderTest {
    private static FileReaderReport reader;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        reader = new CsvFileReader();
    }

    @Test
    public void readCsvFile_IncorrectFile_NotOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can't read this file: 123.csv");
        reader.readCsvFile("123.csv");
    }

    @Test
    public void readCsvFile_EmptyFile_Ok() {
        List<String[]> expected = new ArrayList<>();
        assertEquals("You should return empty list for the argument empty list.",
                expected, reader.readCsvFile("src/test/resources/empty.csv"));
    }

    @Test
    public void readCsvFile_OneDataLineInFile_Ok() {
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"b", "banana", "20"});
        List<String[]> actual = reader.readCsvFile("src/test/resources/onedataline.csv");
        assertEquals("The received from one row data you should put to one list element.",
                expected.get(0), actual.get(0));
    }

    @Test
    public void readCsvFile_manyLinesInFile_Ok() {
        List<String[]> actual = reader.readCsvFile("src/test/resources/tendatalines.csv");
        assertTrue("The list must have 10 elements, because file contains 10 rows",
                actual.size() == 10);
    }
}
