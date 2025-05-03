package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParcingCsvServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParsingServiceTest {
    private static final String SEPARATOR = ",";
    private static final String FRUIT = "peach";
    private static final String VALID_OPERATION = "b";
    private static final String INVALID_OPERATION = "e";
    private static final int AMOUNT = 10;
    private static final String VALID_STRING =
            VALID_OPERATION + SEPARATOR + FRUIT + SEPARATOR + AMOUNT;
    private static final String INVALID_STRING_ONE_VALUE = FRUIT;
    private static final String INVALID_STRING_BAD_OPERATION =
            INVALID_OPERATION + SEPARATOR + FRUIT + SEPARATOR + AMOUNT;
    private List<String> stringList;
    private FruitTransaction fruitTransaction;
    private List<FruitTransaction> fruitTransactions;
    private ParsingService parsingService;

    @BeforeEach
    void setUp() {
        parsingService = new ParcingCsvServiceImpl();
        stringList = new ArrayList<>();
        stringList.add(System.lineSeparator());
        stringList.add(VALID_STRING);
        fruitTransaction = new FruitTransaction(FRUIT,
                FruitTransaction.Operation.BALANCE, AMOUNT);
        fruitTransactions = new ArrayList<>();
        fruitTransactions.add(fruitTransaction);
    }

    @AfterEach
    void tearDown() {
        stringList.clear();
        fruitTransactions.clear();
    }

    @Test
    void parsingDataFromListOfStrings_validInput_ok() {
        List<FruitTransaction> expected = fruitTransactions;
        Assert.assertEquals(expected, parsingService.parsingDataFromListOfStrings(stringList));
    }

    @Test
    void parsingDataFromListOfStrings_invalidInput_notOk() {
        stringList.add(INVALID_STRING_ONE_VALUE);
        Assert.assertThrows(RuntimeException.class, () ->
                parsingService.parsingDataFromListOfStrings(stringList));
    }

    @Test
    void parseOperationFromInput_invalidOperation_notOk() {
        stringList.add(INVALID_STRING_BAD_OPERATION);
        Assert.assertThrows(RuntimeException.class, () ->
                parsingService.parsingDataFromListOfStrings(stringList));
    }
}
