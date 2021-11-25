package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void reader_pathIsNull_notOk() {
        readerService.readFromFile(null);
    }

    @Test (expected = RuntimeException.class)
    public void reader_pathIsEmpty_notOk() {
        readerService.readFromFile("");
    }

    @Test (expected = RuntimeException.class)
    public void writer_pathContainsOnlySpaces_notOk() {
        readerService.readFromFile("      ");
    }

    @Test (expected = RuntimeException.class)
    public void reader_fileIsNotExist_notOk() {
        readerService.readFromFile("src/test/resources/???.csv");
    }

    @Test
    public void reader_fileIsEmpty_ok() {
        List<String> expected = List.of();
        assertEquals("Expected true, but was false", expected,
                readerService.readFromFile("src/test/resources/emptyFileReader.csv"));
    }

    @Test
    public void reader_fileWithEmptyLines_ok() {
        List<String> expected = List.of("", "", "");
        assertEquals("Expected true, but was false", expected,
                readerService.readFromFile("src/test/resources/emptyLinesReader.csv"));
    }

    @Test
    public void reader_fileWithDifferentCharacters_ok() {
        List<String> expected = List.of("`*^$@!$%^", "?.'~~|_)(");
        assertEquals("Expected true, but was false", expected,
                readerService.readFromFile("src/test/resources/differentCharactersReader.csv"));
    }

    @Test
    public void reader_validData_ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        assertEquals("Expected true, but was false", expected,
                readerService.readFromFile("src/test/resources/validDataReader.csv"));
    }
}
