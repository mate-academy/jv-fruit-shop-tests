package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class FruitShopImplTest {
    private static String FILE_FOR_READING = "src/test/resources/database.csv";
    private static String FILE_FOR_WRITING = "src/test/resources/towrite.csv";

    @Test
    public void readFile_ValidFilePath_Ok() throws IOException {
        List<String> expected = Arrays.asList("p,banana,48");
        List<String> actual = new CsvFileReaderImpl().readFile(
                String.valueOf(Path.of(FILE_FOR_READING)));
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFile_CheckEmptyLine_NotOk() {
        new CsvFileReaderImpl().readFile(" ");
    }

    @Test (expected = RuntimeException.class)
    public void readFile_InValidFilePath_NotOk() {
        new CsvFileReaderImpl().readFile("src/test/resources/database");

    }

    @Test (expected = RuntimeException.class)
    public void readFile_NullCheck_NotOk() {
        new CsvFileReaderImpl().readFile(null);
    }

    @Test
    public void writeToFile_ValidData_Ok() {
        new CsvFileWriterImpl().writeToFile(FILE_FOR_WRITING, "banana,48");
        List<String> expected = new ArrayList<>(Collections.singleton("banana,48"));
        List<String> actual = new CsvFileReaderImpl().readFile(FILE_FOR_WRITING);
        assertEquals(actual, expected);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_NullData_NotOk() {
        new CsvFileWriterImpl().writeToFile("InvalidFilePass", null);
    }

}
