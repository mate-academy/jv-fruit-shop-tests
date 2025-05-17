package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.models.FruitTransaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImpTest {
    private static final int ONE_ELEMENT = 1;
    private static final int ZERO_POSITION = 0;
    private static final int TYPICAL_QUANTITY_TEN = 10;
    private static final String FRUIT_APPLE = "apple";

    private static final String CORRECT_LINE_BALANCE = "b,apple,10";
    private static final String CORRECT_LINE_RETURN = "r,apple,10";
    private static final String CORRECT_LINE_PURCHASE = "p,apple,10";
    private static final String CORRECT_LINE_SUPPLY = "s,apple,10";

    private static final String INCORRECT_LINE_LETTERS_INSTEAD_OF_NUMBER = "b,apple,ten";
    private static final String INCORRECT_LINE_ZERO_NUMBER = "b,apple,0";
    private static final String INCORRECT_LINE_NEGATIVE_NUMBER = "b,apple,-10";
    private static final String INCORRECT_LINE_MISSING_QUANTITY = "b,apple";
    private static final String INCORRECT_LINE_MISSING_OPERATION = "apple,10";
    private static final String INCORRECT_LINE_UNKNOWN_OPERATION = "x,apple,10";
    private static final String INCORRECT_LINE_WRONG_SEPARATOR = "b;apple;10";
    private static final String INCORRECT_LINE_EXTRA_ELEMENT = "b,apple,10,extra";
    private static final String INCORRECT_LINE_NULL_ENTRY = null;
    private static final String INCORRECT_LINE_EMPTY_STRING = "";

    private DataConverter dataConverter;
    private List<String> list;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImp();
    }

    @Test
    void convertToTransaction_balanceOperation_ok() {
        list = List.of(CORRECT_LINE_BALANCE);
        List<FruitTransaction> result = dataConverter.convertToTransaction(list);
        assertEquals(ONE_ELEMENT, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(ZERO_POSITION).getOperation());
        assertEquals(FRUIT_APPLE, result.get(ZERO_POSITION).getFruit());
        assertEquals(TYPICAL_QUANTITY_TEN, result.get(ZERO_POSITION).getQuantity());
    }

    @Test
    void convertToTransaction_purchaseOperation_ok() {
        list = List.of(CORRECT_LINE_PURCHASE);
        List<FruitTransaction> result = dataConverter.convertToTransaction(list);
        assertEquals(ONE_ELEMENT, result.size());
        assertEquals(FruitTransaction.Operation.PURCHASE, result.get(ZERO_POSITION).getOperation());
    }

    @Test
    void convertToTransaction_returnOperation_ok() {
        list = List.of(CORRECT_LINE_RETURN);
        List<FruitTransaction> result = dataConverter.convertToTransaction(list);
        assertEquals(ONE_ELEMENT, result.size());
        assertEquals(FruitTransaction.Operation.RETURN, result.get(ZERO_POSITION).getOperation());
    }

    @Test
    void convertToTransaction_supplyOperation_ok() {
        list = List.of(CORRECT_LINE_SUPPLY);
        List<FruitTransaction> result = dataConverter.convertToTransaction(list);
        assertEquals(ONE_ELEMENT, result.size());
        assertEquals(FruitTransaction.Operation.SUPPLY, result.get(ZERO_POSITION).getOperation());
    }

    @Test
    void convertToTransaction_emptyList_returnsEmptyList() {
        List<FruitTransaction> result = dataConverter.convertToTransaction(Collections.emptyList());
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void convertToTransaction_lettersInsteadOfNumber_throwsException() {
        list = List.of(INCORRECT_LINE_LETTERS_INSTEAD_OF_NUMBER);
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(list));
    }

    @Test
    void convertToTransaction_zeroQuantity_throwsException() {
        list = List.of(INCORRECT_LINE_ZERO_NUMBER);
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(list));
    }

    @Test
    void convertToTransaction_negativeQuantity_throwsException() {
        list = List.of(INCORRECT_LINE_NEGATIVE_NUMBER);
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(list));
    }

    @Test
    void convertToTransaction_wrongSeparator_throwsException() {
        list = List.of(INCORRECT_LINE_WRONG_SEPARATOR);
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(list));
    }

    @Test
    void convertToTransaction_unknownOperation_throwsException() {
        list = List.of(INCORRECT_LINE_UNKNOWN_OPERATION);
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(list));
    }

    @Test
    void convertToTransaction_missingQuantity_throwsException() {
        list = List.of(INCORRECT_LINE_MISSING_QUANTITY);
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(list));
    }

    @Test
    void convertToTransaction_missingOperation_throwsException() {
        list = List.of(INCORRECT_LINE_MISSING_OPERATION);
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(list));
    }

    @Test
    void convertToTransaction_extraElement_throwsException() {
        list = List.of(INCORRECT_LINE_EXTRA_ELEMENT);
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(list));
    }

    @Test
    void convertToTransaction_listContainsNullEntry_throwsException() {
        list = new ArrayList<>();
        list.add(INCORRECT_LINE_NULL_ENTRY);
        assertThrows(NullPointerException.class,
                () -> dataConverter.convertToTransaction(list));
    }

    @Test
    void convertToTransaction_emptyString_throwsException() {
        list = new ArrayList<>();
        list.add(INCORRECT_LINE_EMPTY_STRING);
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(list));
    }
}
