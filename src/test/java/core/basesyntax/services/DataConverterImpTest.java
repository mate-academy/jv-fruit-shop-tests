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
    private static final String correctLineBalanceOperation = "b,apple,10";
    private static final String correctLineReturnOperation = "r,apple,10";
    private static final String correctLinePurchaseOperation = "p,apple,10";
    private static final String correctLineSupplyOperation = "s,apple,10";
    private static final String inCorrectLineNumberByLetters = "b,apple,ten";
    private static final String inCorrectLineNumberZero = "b,apple,0";
    private static final String inCorrectLineNegativeNumber = "b,apple,-10";
    private static final String inCorrectLineMissingElementAfter = "b,apple";
    private static final String inCorrectLineMissingElementBefore = "apple,10";
    private static final String inCorrectLineWithWrongElement = "x,apple,10";
    private static final String inCorrectLineWithWrongSeparator = "b;apple;10";
    private static final String inCorrectLineExtraElement = "b,apple,10,extra";
    private static final String inCorrectLineMissingElementWithCommasBetween = "b,apple";
    private static final String inCorrectLineMissingElementWithCommasBefore = "b,apple";
    private static final String inCorrectLineMissingElementWithCommasAfter = "b,apple";
    private static final String inCorrectLineWithNull = null;
    private static final String inCorrectLineWithEmptyString = "";

    private DataConverter dataConverter;
    private List<String> list;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImp();
    }

    @Test
    void checkingIsEverything_WorkingWith_BalanceOperation() {
        list = List.of(correctLineBalanceOperation);
        List<FruitTransaction> transaction = dataConverter.convertToTransaction(list);
        assertEquals(ONE_ELEMENT, transaction.size());
        assertEquals(FruitTransaction.Operation.BALANCE,
                transaction.get(ZERO_POSITION).getOperation());
        assertEquals(FRUIT_APPLE, transaction.get(ZERO_POSITION).getFruit());
        assertEquals(TYPICAL_QUANTITY_TEN, transaction.get(ZERO_POSITION).getQuantity());
    }

    @Test
    void checkingIsEverything_WorkingWith_PurchaseOperation() {
        list = List.of(correctLinePurchaseOperation);
        List<FruitTransaction> transaction = dataConverter.convertToTransaction(list);
        assertEquals(ONE_ELEMENT, transaction.size());
        assertEquals(FruitTransaction.Operation.PURCHASE,
                transaction.get(ZERO_POSITION).getOperation());
        assertEquals(FRUIT_APPLE, transaction.get(ZERO_POSITION).getFruit());
        assertEquals(TYPICAL_QUANTITY_TEN, transaction.get(ZERO_POSITION).getQuantity());
    }

    @Test
    void checkingIsEverything_WorkingWith_ReturnOperation() {
        list = List.of(correctLineReturnOperation);
        List<FruitTransaction> transaction = dataConverter.convertToTransaction(list);
        assertEquals(ONE_ELEMENT, transaction.size());
        assertEquals(FruitTransaction.Operation.RETURN,
                transaction.get(ZERO_POSITION).getOperation());
        assertEquals(FRUIT_APPLE, transaction.get(ZERO_POSITION).getFruit());
        assertEquals(TYPICAL_QUANTITY_TEN, transaction.get(ZERO_POSITION).getQuantity());
    }

    @Test
    void checkingIsEverything_WorkingWith_SupplyOperation() {
        list = List.of(correctLineSupplyOperation);
        List<FruitTransaction> transaction = dataConverter.convertToTransaction(list);
        assertEquals(ONE_ELEMENT, transaction.size());
        assertEquals(FruitTransaction.Operation.SUPPLY,
                transaction.get(ZERO_POSITION).getOperation());
        assertEquals(FRUIT_APPLE, transaction.get(ZERO_POSITION).getFruit());
        assertEquals(TYPICAL_QUANTITY_TEN, transaction.get(ZERO_POSITION).getQuantity());
    }

    @Test
    void convertToTransaction_EmptyList_ReturnsEmptyList() {
        List<FruitTransaction> result = dataConverter.convertToTransaction(Collections.emptyList());
        assertNotNull(result, "Method mustn't return null");
        assertEquals(0, result.size(), "List must be empty");
    }

    @Test
    void convertToTransaction_IncorrectNumber_ThrowsException() {
        list = List.of(inCorrectLineNumberByLetters);
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(list),
                "Expected exception due to incorrect number format");
    }

    @Test
    void convertToTransaction_IncorrectNumberZero_ThrowsException() {
        list = List.of(inCorrectLineNumberZero);
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(list),
                "Expected exception due to incorrect number format");
    }

    @Test
    void convertToTransaction_IncorrectNegativeNumber_ThrowsException() {
        list = List.of(inCorrectLineNegativeNumber);
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(list),
                "Expected exception due to incorrect number format");
    }

    @Test
    void convertToTransaction_IncorrectSeparator_ThrowsException() {
        list = List.of(inCorrectLineWithWrongSeparator);
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(list),
                "Expected exception due to incorrect separator usage");
    }

    @Test
    void convertToTransaction_IncorrectElement_ThrowsException() {
        list = List.of(inCorrectLineWithWrongElement);
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(list),
                "Expected exception due to unrecognized operation type");
    }

    @Test
    void checkLineWithAMissingElement_Before() {
        list = List.of(inCorrectLineMissingElementBefore);
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(list),
                "Expected exception due to missing operation type");
    }

    @Test
    void checkLineWithAMissingElement_After() {
        list = List.of(inCorrectLineMissingElementAfter);
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(list),
                "Expected exception due to missing quantity field");
    }

    @Test
    void checkLineWithAMissingElement_AfterWithComma() {
        list = List.of(inCorrectLineMissingElementWithCommasAfter);
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(list),
                "Expected exception due to missing element after comma");
    }

    @Test
    void checkLineWithAMissingElement_BeforeWithComma() {
        list = List.of(inCorrectLineMissingElementWithCommasBefore);
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(list),
                "Expected exception due to missing element before comma");
    }

    @Test
    void checkLineWithAMissingElement_BetweenWithComma() {
        list = List.of(inCorrectLineMissingElementWithCommasBetween);
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(list),
                "Expected exception due to missing element between commas");
    }

    @Test
    void checkLineWithExtraElement() {
        list = List.of(inCorrectLineExtraElement);
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(list),
                "Expected exception due to extra unexpected element");
    }

    @Test
    void checkLineWithNull() {
        assertThrows(NullPointerException.class, () -> dataConverter.convertToTransaction(null),
                "Expected NullPointerException due to null input");
    }

    @Test
    void checkLineWithDataNull() {
        list = new ArrayList<>();
        list.add(inCorrectLineWithNull);
        assertThrows(NullPointerException.class, () -> dataConverter.convertToTransaction(list),
                "Expected NullPointerException due to null entry in the list");
    }

    @Test
    void checkLineWithEmptyString() {
        list = new ArrayList<>();
        list.add(inCorrectLineWithEmptyString);
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(list),
                "Expected NullPointerException due to empty list");
    }
}
