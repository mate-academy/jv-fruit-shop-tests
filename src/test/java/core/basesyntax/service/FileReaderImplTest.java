package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String INPUT_1_FILE_NAME = "input1.csv";
    private static final String INPUT_2_FILE_NAME = "input2.csv";
    private static final String INPUT_3_FILE_NAME = "input3.csv";
    private static final String INVALID_FILE_NAME = "input4.csv";
    private static FileReader fileReader;
    private static ClassLoader classLoader;

    @BeforeAll
    static void beforeAll() {
        classLoader = FileReaderImplTest.class.getClassLoader();
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFromFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileReader.readFromFile(new File(INVALID_FILE_NAME)));
    }

    @Test
    void readFromInput1File_Ok() {
        List<String> actual = readFromFile(INPUT_1_FILE_NAME);
        List<String> expected = new ArrayList<>(List.of("type,fruit,quantity",
                "b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10", "p,apple,20",
                "p,banana,5", "s,banana,50"));
        assertEquals(expected, actual);
    }

    @Test
    void readFromInput2File_Ok() {
        List<String> actual = readFromFile(INPUT_2_FILE_NAME);
        List<String> expected = new ArrayList<>(List.of("type,fruit,quantity",
                "b,banana,200", "b,apple,150", "p,banana,100", "s,banana,25",
                "r,apple,10", "r,banana,20", "p,banana,5", "s,banana,15"));
        assertEquals(expected, actual);
    }

    @Test
    void readFromInput3File_Ok() {
        List<String> actual = readFromFile(INPUT_3_FILE_NAME);
        List<String> expected = new ArrayList<>(List.of("type,fruit,quantity",
                "b,pear,200", "b,apple,150", "b,banana,100", "p,pear,80",
                "s,banana,25", "r,apple,10", "r,pear,20", "p,banana,5",
                "s,banana,15", "p,banana,20"));
        assertEquals(expected, actual);
    }

    private List<String> readFromFile(String fileName) {
        URL resources = classLoader.getResource(fileName);
        return fileReader.readFromFile(new File(resources.getFile()));
    }
}
