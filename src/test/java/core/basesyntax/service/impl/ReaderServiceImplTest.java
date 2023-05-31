package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static List<String> expectedLines;
    private static List<String> actualLines;

    @BeforeClass
    public static void setUp() throws Exception {
        expectedLines = new ArrayList<>();
        actualLines = new ArrayList<>();
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_OneString_ok() {
        expectedLines.add("First line");
        actualLines = readerService.readFromFile("src/test/resources/oneLine.csv");
        assertEquals(actualLines, expectedLines);
    }

    @Test
    public void readFromFile_TwoStrings_ok() {
        expectedLines.add("First line");
        expectedLines.add("Second line");
        actualLines = readerService.readFromFile("src/test/resources/twoLine.csv");
        assertEquals(actualLines, expectedLines);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_FileNameNull_throwRuntimeException() {
        readerService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_WrongFileName_throwRuntimeException() {
        readerService.readFromFile("/inprut.csv");
    }

    @After
    public void tearDown() {
        actualLines.clear();
        expectedLines.clear();
    }
}
