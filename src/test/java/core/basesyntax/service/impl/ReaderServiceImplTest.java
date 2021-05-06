package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final ReaderService READER_SERVICE = new ReaderServiceImpl();

    @Test
    public void read_custom_Ok() {
        String fileName = "src/test/resources/TestInput.csv";
        List<String> expected = List.of("banana,apple,tomato");
        List<String> actual = READER_SERVICE.readFromFile(fileName);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void read_customInput_Ok() {
        String fileName = "src/test/resources/ActualInput.csv";
        List<String> expected = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = READER_SERVICE.readFromFile(fileName);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void read_Empty_Ok() {
        String fileName = "src/test/resources/EmptyInput.csv";
        List<String> expected = List.of();
        List<String> actual = READER_SERVICE.readFromFile(fileName);
        Assert.assertEquals(expected, actual);
    }
}
