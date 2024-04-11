package core.basesyntax.service.impl;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.service.Reader;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    private static final String INCORRECT_PATH_OF_INPUT_FILE
            = "src/test/service/resources/input.csv";
    private static final String INCORRECT_NAME_OF_INPUT_FILE
            = "src/test/service/resources/input.csv";
    private static final String CORRECT_PATH_OF_INPUT_FILE = "src/test/resources/input.csv";
    private static Reader reader;

    @BeforeEach
    void setUp() {
        reader = new ReaderImpl();
    }

    @Test
    void readDataFromFile_nullData_notOk() {
        Assert.assertThrows(NullPointerException.class, () ->
                reader.readDataFromFile(null));
    }

    @Test
    void readDataFromFile_incorrectPathOfInputFile_notOk() {
        Assert.assertThrows(FruitShopException.class, () ->
                reader.readDataFromFile(INCORRECT_PATH_OF_INPUT_FILE));
    }

    @Test
    void readDataFromFile_incorrectNameOfInputFile_notOk() {
        Assert.assertThrows(FruitShopException.class, () ->
                reader.readDataFromFile(INCORRECT_NAME_OF_INPUT_FILE));
    }

    @Test
    void readDataFromFile_correctPathOfInputFile_ok() {
        String expected = "b,banana,20 "
                + "b,apple,100 "
                + "s,banana,100 "
                + "p,banana,13 "
                + "r,apple,10 "
                + "p,apple,20 "
                + "p,banana,5 "
                + "s,banana,50";
        String actual = reader.readDataFromFile(CORRECT_PATH_OF_INPUT_FILE);
        Assertions.assertEquals(expected, actual);
    }
}
