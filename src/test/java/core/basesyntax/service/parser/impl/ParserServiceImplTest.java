package core.basesyntax.service.parser.impl;

import core.basesyntax.enumeration.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.parser.ParserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class ParserServiceImplTest {
    private static final int COUNT_OF_ELEMENT_AFTER_PARSING = 1;
    private static final int QUANTITY_OF_FRUIT = 20;
    private static final String NAME_OF_FRUIT = "banana";
    private static final String TITLE_OF_INPUT_DATA = "type,fruit,quantity";
    private ParserService parserService;
    private List<FruitTransaction> fruitTransactions;
    private List<String> sourceData;

    @BeforeEach
    public void setUp() {
        parserService = new ParserServiceImpl();
        fruitTransactions = new ArrayList<>();
        sourceData = new ArrayList<>();
    }

    @Test
    void parserService_withValidData_ok() {
        sourceData.add(TITLE_OF_INPUT_DATA);
        sourceData.add("b,banana,20");
        List<FruitTransaction> resultFruitTransactions = parserService.parseData(sourceData);
        Assertions.assertEquals(COUNT_OF_ELEMENT_AFTER_PARSING, resultFruitTransactions.size());

        FruitTransaction firstFruitTransaction = resultFruitTransactions.get(0);
        Assertions.assertEquals(Operation.BALANCE, firstFruitTransaction.getOperation());
        Assertions.assertEquals(NAME_OF_FRUIT, firstFruitTransaction.getFruit());
        Assertions.assertEquals(QUANTITY_OF_FRUIT, firstFruitTransaction.getQuantity());
    }

    @Test
    void parserService_withWrongEnumCode_notOk() {
        sourceData.add(TITLE_OF_INPUT_DATA);
        sourceData.add("invalidCode,banana,20");
        NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class,
                () -> parserService.parseData(sourceData));
        Assertions.assertEquals("No value present", exception.getMessage());
    }

    @Test
    void parserService_withEmptyEnumCode_notOk() {
        sourceData.add(TITLE_OF_INPUT_DATA);
        sourceData.add(",banana,20");
        NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class,
                () -> parserService.parseData(sourceData));
        Assertions.assertEquals("No value present", exception.getMessage());
    }

    @Test
    void parserService_withNull_notOk() {
        sourceData.add(TITLE_OF_INPUT_DATA);
        sourceData.add(null);
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> parserService.parseData(sourceData));
        Assertions.assertEquals("String cannot be null or empty.", exception.getMessage());
    }

    @Test
    void parserService_stringIsEmpty_notOk() {
        sourceData.add(TITLE_OF_INPUT_DATA);
        sourceData.add("");
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> parserService.parseData(sourceData));
        Assertions.assertEquals("String cannot be null or empty.", exception.getMessage());
    }

    @Test
    void parserService_stringWithoutFruit_notOk() {
        sourceData.add(TITLE_OF_INPUT_DATA);
        sourceData.add("b,20");
        IndexOutOfBoundsException exception = Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> parserService.parseData(sourceData));
        Assertions.assertEquals("Index 2 out of bounds for length 2", exception.getMessage());
    }

    @Test
    void parserService_stringWithoutQuantity_notOk() {
        sourceData.add(TITLE_OF_INPUT_DATA);
        sourceData.add("b,banana");
        IndexOutOfBoundsException exception = Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> parserService.parseData(sourceData));
        Assertions.assertEquals("Index 2 out of bounds for length 2", exception.getMessage());
    }
}
