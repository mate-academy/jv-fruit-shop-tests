package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;

    @BeforeAll
    public static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_fileNotExists_notOk() {
        String fileName = "src/test/resources/wrong.file";
        assertThrows(RuntimeException.class, () -> fileReader.readFromFile(fileName));
    }

    @Test
    public void readFromFile_fileIsEmpty_Ok() {
        List<String> expected = Collections.emptyList();
        List<String> actual = fileReader.readFromFile("src/test/resources/empty.csv");
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_singleLine_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        List<String> actual = fileReader.readFromFile("src/test/resources/single.csv");
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_regularCase_ok() {
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
        List<String> actual = fileReader.readFromFile("src/test/resources/testdata.csv");
        assertEquals(expected, actual);
    }
}
