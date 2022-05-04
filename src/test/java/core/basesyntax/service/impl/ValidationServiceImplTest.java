package core.basesyntax.service.impl;

import core.basesyntax.service.ValidationService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidationServiceImplTest {
    private static ValidationService validationService;
    private static List<String> readData;

    @BeforeClass
    public static void beforeClass() {
        validationService = new ValidationServiceImpl();
    }

    @Before
    public void setUp() {
        readData = new ArrayList<>();
        readData.add("type,fruit,quantity");
        readData.add("b,banana,20");
        readData.add("b,apple,100");
        readData.add("s,banana,100");
        readData.add("p,banana,13");
        readData.add("r,apple,10");
        readData.add("p,apple,20");
        readData.add("p,banana,5");
        readData.add("s,banana,50");
    }

    @After
    public void tearDown() {
        readData.clear();
    }

    @Test
    public void validateData_ok() {
        List<String> actual = validationService.validate(readData);
        readData.remove(0);
        List<String> expected = readData;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void validateRandomData_ok() {
        List<String> randomData = readData;
        randomData.add("write-off,banana,20");
        randomData.add(1, " ");
        List<String> actual = validationService.validate(randomData);
        readData.remove(0);
        List<String> expected = readData;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void validateEmptyData_ok() {
        readData.clear();
        List<String> actual = validationService.validate(readData);
        List<String> expected = new ArrayList<>();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void validateZeroChar_ok() {
        List<String> elementsStartWithBlank = readData.stream()
                .map(line -> " " + line)
                .collect(Collectors.toList());
        List<String> actual = validationService.validate(elementsStartWithBlank);
        List<String> expected = new ArrayList<>();
        Assert.assertEquals(expected, actual);
    }
}
