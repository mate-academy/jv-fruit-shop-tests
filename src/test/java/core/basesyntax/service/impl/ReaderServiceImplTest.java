package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.Collections;
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
    public void readFromFile_pathIsNull_notOk() {
        readerService.readFromFile(null);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_pathIsEmpty_notOk() {
        readerService.readFromFile("");
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_pathContainsOnlySpaces_notOk() {
        readerService.readFromFile("      ");
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_fileIsNotExist_notOk() {
        readerService.readFromFile("src/test/resources/???.csv");
    }

    @Test
    public void readFromFile_fileIsEmpty_ok() {
        List<String> expected = Collections.emptyList();
        assertEquals(expected, readerService.readFromFile("src/test/resources/emptyFileReader.csv"));
    }

    @Test
    public void readFromFile_fileWithEmptyLines_ok() {
        List<String> expected = List.of("", "", "");
        assertEquals(expected, readerService.readFromFile("src/test/resources/emptyLinesReader.csv"));
    }

    @Test
    public void readFromFile_fileWithDifferentCharacters_ok() {
        List<String> expected = List.of("`*^$@!$%^", "?.'~~|_)(");
        assertEquals(expected, readerService.readFromFile("src/test/resources/differentCharactersReader.csv"));
    }

    @Test
    public void readFromFile_validData_ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        assertEquals(expected, readerService.readFromFile("src/test/resources/validDataReader.csv"));
    }
}
