package core.basesyntax.dao;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataConverterImplTest {
    DataConverterImpl dataConverterImpl;

    @BeforeEach
    void setUp() {
        dataConverterImpl = new DataConverterImpl();
    }

    @Test
    void getDataConverterImplTest_validBalanceOperationInput_ok() {
        FruitTransaction actual = dataConverterImpl.getFromCsvFile("b,banana,1");
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 1);
        assertEquals(expected, actual);
    }

    @Test
    void getDataConverterImplTest_validSupplyOperationInput_ok() {
        FruitTransaction actual = dataConverterImpl.getFromCsvFile("s,banana,1");
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 1);
        assertEquals(expected, actual);
    }

    @Test
    void getDataConverterImplTest_validReturnOperationInput_ok() {
        FruitTransaction actual = dataConverterImpl.getFromCsvFile("r,banana,1");
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 1);
        assertEquals(expected, actual);
    }

    @Test
    void getDataConverterImplTest_validPurchaseOperationInput_ok() {
        FruitTransaction actual = dataConverterImpl.getFromCsvFile("p,banana,1");
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 1);
        assertEquals(expected, actual);
    }

    @Test
    void getDataConverterImplTest_validInputWithSpaces_ok() {
        FruitTransaction actual = dataConverterImpl.getFromCsvFile(" p , banana , 1 ");
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 1);
        assertEquals(expected, actual);
    }

    @Test
    void getDataConverterImplTest_invalidInputForInvalidQuantity_notOk() {
        assertThrows(RuntimeException.class,() -> dataConverterImpl.getFromCsvFile("b,banana,a"));
    }

    @Test
    void getDataConverterImplTest_invalidInputForFourFields_notOk() {
        assertThrows(RuntimeException.class,() -> dataConverterImpl.getFromCsvFile("b,banana,2,4"));
    }

    @Test

    void getDataConverterImplTest_invalidInputWithoutOperation_notOk() {
        assertThrows(RuntimeException.class,() -> dataConverterImpl.getFromCsvFile(",banana,2,4"));
    }

    @Test
    void getDataConverterImplTest_invalidInputForFiveFields_notOk() {
        assertThrows(RuntimeException.class,() -> dataConverterImpl.getFromCsvFile("b,banana,2,4,5"));
    }

    @Test
    void getDataConverterImplTest_invalidInputForEmptyFields_notOk() {
        assertThrows(RuntimeException.class,() -> dataConverterImpl.getFromCsvFile(" "));
    }

    @Test
    void getDataConverterImplTest_invalidNullInput_notOk() {
        assertThrows(RuntimeException.class,() -> dataConverterImpl.getFromCsvFile(null));
    }

}