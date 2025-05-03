package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserServiceImplTest {
    private static final int FIRST_LINE_INDEX = 0;
    private static final String PATH_TO_VALID_INPUT_DATA = "src/test/resources/validInput.csv";
    private static final List<String> INVALID_INPUT_DATA = List.of("fruit,quantity", "b,banana,20");
    private static TransactionParserServiceImpl parser;

    @Before
    public void setUp() {
        parser = new TransactionParserServiceImpl();
    }

    @Test
    public void parseList_validInputData_Ok() {
        List<String> inputData = new FileReaderServiceImpl().readFromFile(PATH_TO_VALID_INPUT_DATA);
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        String fruit = "banana";
        int quantity = 20;
        List<FruitTransaction> actual = parser.parseList(inputData);
        assertEquals(operation, actual.get(FIRST_LINE_INDEX).getOperation());
        assertEquals(fruit, actual.get(FIRST_LINE_INDEX).getFruit());
        assertEquals(quantity, actual.get(FIRST_LINE_INDEX).getQuantity());
    }

    @Test(expected = RuntimeException.class)
    public void parseList_invalidInputData_notOk() {
        parser.parseList(INVALID_INPUT_DATA);
    }

    @Test(expected = NullPointerException.class)
    public void parseList_nullInputData_notOk() {
        parser.parseList(null);
    }

}
