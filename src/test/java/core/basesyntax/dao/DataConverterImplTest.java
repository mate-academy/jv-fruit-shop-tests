package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverterImpl dataConverterImpl;

    @BeforeEach
    void setUp() {
        dataConverterImpl = new DataConverterImpl();
    }

    @Test
    void getFromCsvFile_validBalanceOperationInput_ok() {
        FruitTransaction actual = dataConverterImpl.getFromCsvFile("b,banana,1");
        FruitTransaction expected = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                1);
        assertEquals(expected, actual);
    }

    @Test
    void getFromCsvFile_validSupplyOperationInput_ok() {
        FruitTransaction actual = dataConverterImpl.getFromCsvFile("s,banana,1");
        FruitTransaction expected = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "banana",
                1);
        assertEquals(expected, actual);
    }

    @Test
    void getFromCsvFile_validReturnOperationInput_ok() {
        FruitTransaction actual = dataConverterImpl.getFromCsvFile("r,banana,1");
        FruitTransaction expected = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "banana",
                1);
        assertEquals(expected, actual);
    }

    @Test
    void getFromCsvFile_validPurchaseOperationInput_ok() {
        FruitTransaction actual = dataConverterImpl.getFromCsvFile("p,banana,1");
        FruitTransaction expected = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana",
                1);
        assertEquals(expected, actual);
    }

    @Test
    void getFromCsvFile_validInputWithSpaces_ok() {
        FruitTransaction actual = dataConverterImpl.getFromCsvFile(" p , banana , 1 ");
        FruitTransaction expected = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana",
                1);
        assertEquals(expected, actual);
    }

    @Test
    void getFromCsvFile_invalidInputForInvalidQuantity_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverterImpl.getFromCsvFile("b,banana,a"));
    }

    @Test
    void getFromCsvFile_invalidInputForFourFields_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverterImpl.getFromCsvFile("b,banana,2,4"));
    }

    @Test

    void getFromCsvFile_invalidInputWithoutOperation_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverterImpl.getFromCsvFile(",banana,2,4"));
    }

    @Test
    void getFromCsvFile_invalidOperation_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverterImpl.getFromCsvFile("a,banana,2,4"));
    }

    @Test
    void getFromCsvFile_invalidInputForFiveFields_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverterImpl.getFromCsvFile("b,banana,2,4,5"));
    }

    @Test
    void getFromCsvFile_invalidInputForEmptyFields_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverterImpl.getFromCsvFile(" "));
    }

    @Test
    void getFromCsvFile_invalidNullInput_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverterImpl.getFromCsvFile(null));
    }

    @Test
    void convertToTransaction_invalidNullInput_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverterImpl.convertToTransaction(null));
    }

    @Test
    void convertToTransaction_invalidEmptyInput_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverterImpl.convertToTransaction(List.of()));
    }

    @Test
    void convertToTransaction_validBalanceOperation_Ok() {
        List<FruitTransaction> actual = dataConverterImpl
                .convertToTransaction(List.of("firstLine", "b,banana,1"));

        List<FruitTransaction> expected = List.of(
                new FruitTransaction(
                        FruitTransaction.Operation.BALANCE,
                        "banana",
                        1));
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransaction_validPurchaseOperation_Ok() {
        List<FruitTransaction> actual = dataConverterImpl
                .convertToTransaction(List.of("firstLine", "p,banana,1"));

        List<FruitTransaction> expected = List.of(
                new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE,
                        "banana",
                        1));
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransaction_validSupplyOperation_Ok() {
        List<FruitTransaction> actual = dataConverterImpl
                .convertToTransaction(List.of("firstLine", "s,banana,1"));

        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "banana",
                        1));
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransaction_validReturnOperation_Ok() {
        List<FruitTransaction> actual = dataConverterImpl
                .convertToTransaction(List.of("firstLine", "r,banana,1"));
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(
                        FruitTransaction.Operation.RETURN,
                        "banana",
                        1));
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransaction_validDifferentOperation_Ok() {
        List<FruitTransaction> actual = dataConverterImpl.convertToTransaction(
                List.of("firstLine",
                        "p,banana,1",
                        "r,banana,1",
                        "s,banana,1"));

        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 1),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 1),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 1)
        );
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransaction_validInputWithBlankLines_Ok() {
        List<FruitTransaction> actual = dataConverterImpl.convertToTransaction(
                List.of("firstLine",
                        " ",
                        "p,banana,1",
                        "r,banana,1",
                        " ",
                        "s,banana,1"));

        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 1),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 1),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 1)
        );
        assertEquals(expected, actual);
    }
}
