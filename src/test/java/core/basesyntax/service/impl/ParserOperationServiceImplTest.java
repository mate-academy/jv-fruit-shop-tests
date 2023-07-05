package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserOperationService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserOperationServiceImplTest {
    private static final String SEPARATOR_IN_LINE = ",";
    private static final int INDEX_FOR_OPERATION_IN_STRING = 0;
    private static final int INDEX_FOR_PRODUCT_NAME_IN_STRING = 1;
    private static final int INDEX_FOR_PRODUCT_VALUE_IN_STRING = 2;
    private ParserOperationService parserOperationService;
    private List<String> data = Arrays.asList("b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10");

    @BeforeEach
    public void setUp() {
        parserOperationService = new ParserOperationServiceImpl();
    }

    @Test
    public void test_Parser_NullData_NotOk() {
        assertThrows(NullPointerException.class,
                () -> parserOperationService.parser(null));
    }

    @Test
    public void test_Parser_EmptyData_Ok() {
        data = new ArrayList<>();
        assertEquals(0, parserOperationService.parser(data).size());
    }

    @Test
    public void test_Parser_Data_Ok() {
        List<FruitTransaction> fruitTransactionList;
        fruitTransactionList = parserOperationService.parser(data);
        assertEquals(4, fruitTransactionList.size());
        for (int i = 0; i < data.size(); i++) {
            assertEquals(data.get(i)
                            .split(SEPARATOR_IN_LINE)[INDEX_FOR_OPERATION_IN_STRING],
                    fruitTransactionList.get(i).getOperation().getCode());
            assertEquals(data.get(i)
                            .split(SEPARATOR_IN_LINE)[INDEX_FOR_PRODUCT_NAME_IN_STRING],
                    fruitTransactionList.get(i).getFruit());
            assertEquals(Integer.parseInt(data.get(i)
                            .split(SEPARATOR_IN_LINE)[INDEX_FOR_PRODUCT_VALUE_IN_STRING]),
                    fruitTransactionList.get(i).getQuantity());
        }
    }
}
