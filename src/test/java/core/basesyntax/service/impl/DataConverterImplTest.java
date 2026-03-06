package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    private DataConverter dataConverter = new DataConverterImpl();
    private List<String> listOfTransactions = List.of("op,furit,qty",
            "b,apple,10");
    private List<String> invalidListOfTransactionsWrongSeparator = List.of("op.furit.qty",
            "b.apple.10");
    private List<String> invalidListOfTransactionsMoreColumns = List.of("op,furit,qty,additionl",
            "b,apple,10,extra");
    private List<String> invalidListOfTransactionsLessColumns = List.of("op,furit",
            "b,apple");
    private List<String> invalidListOfTransactionsDoubleQuantity = List.of("op,furit,qty",
            "b,apple,20.8");
    private List<String> invalidListOfTransactionsEmpty = List.of("");
    private List<String> invalidListOfTransactionsIncorrectOperation = List.of("op,furit,qty",
            "z,apple,10");

    @Test
    void convertToTransactions_validData_returnList_ok() {
        List<FruitTransaction> result = dataConverter.convertToTransactions(listOfTransactions);
        int expected = listOfTransactions.size() - 1;
        int actual = result.size();
        assertNotNull(result);
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransactions_validData_parseFruitName_ok() {
        List<FruitTransaction> result = dataConverter.convertToTransactions(listOfTransactions);
        String expected = "apple";
        String actual = result.get(0).getFruit();
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransactions_validData_parseOperation_ok() {
        List<FruitTransaction> result = dataConverter.convertToTransactions(listOfTransactions);
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = result.get(0).getOperation();
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransactions_validData_parseQuantity_ok() {
        List<FruitTransaction> result = dataConverter.convertToTransactions(listOfTransactions);
        int expected = 10;
        int actual = result.get(0).getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransactions_invalidData_invalidSeparator_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransactions(invalidListOfTransactionsWrongSeparator));

    }

    @Test
    void convertToTransactions_invalidData_moreColumns_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransactions(invalidListOfTransactionsMoreColumns));

    }

    @Test
    void convertToTransactions_invalidData_lessColumns_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransactions(invalidListOfTransactionsLessColumns));

    }

    @Test
    void convertToTransactions_invalidData_doubleInsteadOfInt_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransactions(invalidListOfTransactionsDoubleQuantity));

    }

    @Test
    void convertToTransactions_invalidData_emptyInput_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransactions(invalidListOfTransactionsEmpty));

    }

    @Test
    void convertToTransactions_invalidData_incorrectOperationCode_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransactions(
                        invalidListOfTransactionsIncorrectOperation));

    }
}
