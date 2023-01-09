package core.basesyntax.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderServiceImplTest {
    private static final String EMPTY_FILE_NAME = "src/test/resources/test1.csv";
    private static final String FIRST_FILE = "src/test/resources/FruitShop.csv";
    private static final String PATH_TO_NON_EXISTING_FILE = "src/Main/java/resources/cars.csv";
    private static final List<String> EXPECTED_RESULT = Arrays.asList("type;fruit;quantity",
            "b;banana;20", "b;apple;100", "s;banana;100", "p;banana;13", "r;apple;10",
            "p;apple;20", "p;banana;5", "s;banana;50");
    private static final List<String> EMPTY_LIST_RESULT = new ArrayList<>();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void readFromFile() {
        ReaderServiceImpl readerService = new ReaderServiceImpl();
        List<String> actualResult = readerService.readFromFile(FIRST_FILE);
        Assert.assertEquals(EXPECTED_RESULT, actualResult);
    }

    @Test
    public void readFromEmptyFile() {
        ReaderServiceImpl readerService = new ReaderServiceImpl();
        List<String> actualResult = readerService.readFromFile(EMPTY_FILE_NAME);
        Assert.assertEquals("Test failed! You should returned empty list.",
                EMPTY_LIST_RESULT, actualResult);
    }

    @Test
    public void reader_throw_RuntimeException() {
        ReaderServiceImpl readerService = new ReaderServiceImpl();
        exception.expect(RuntimeException.class);
        readerService.readFromFile(PATH_TO_NON_EXISTING_FILE);
    }
}
