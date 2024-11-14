package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static FileReaderService fileReader;
    private static final String PATH = "src/test/resources/reportToRead.csv";
    private static final String WRONG_PATH = "src/test/resources/wrongPath.csv";

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderServiceImpl();
    }

    @Test
    void read_fromFileRightPath_itOk() {
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
        List<String> actual = fileReader.read(PATH);
        assertEquals(expected, actual);
    }

    @Test
    void read_fromFileWrongPass_throwException() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () ->
                fileReader.read(WRONG_PATH));
        String actual = exception.getMessage();
        String expected = "Error reading file at path :" + WRONG_PATH;
        assertEquals(expected, actual);
    }
}
