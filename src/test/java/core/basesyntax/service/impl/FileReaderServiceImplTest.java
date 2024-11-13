package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private FileReaderService fileReader = new FileReaderServiceImpl();

    @Test
    void read_fromFileRightPath_itOk() {
        String path = "src/test/resources/reportToRead.csv";
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
        List<String> actual = fileReader.read(path);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void read_fromFileWrongPass_throwException() {
        String path = "src/test/resources/wrongPath.csv";
        Exception exception = Assertions.assertThrows(RuntimeException.class, () ->
                fileReader.read(path));
        String actual = exception.getMessage();
        String expected = "Error reading file at path :" + path;
        Assertions.assertEquals(expected, actual);
    }
}
