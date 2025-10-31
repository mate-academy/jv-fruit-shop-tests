package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterTest {
    private DataConverter dataConverter;

    @BeforeEach
    void initializeService() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_nullList_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = dataConverter.convertToTransaction(null);
        assertEquals(expected, actual, "New empty list haven't created for null input");
    }

    @Test
    void convertToTransaction_emptyList_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = dataConverter.convertToTransaction(new ArrayList<>());
        assertEquals(expected, actual, "New empty list haven't created for empty list input");
    }

    @Test
    void convertToTransaction_balanceItemOnList_ok() {
        List<String> list = new ArrayList<>();
        list.add("b,banana,20");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        List<FruitTransaction> actual = dataConverter.convertToTransaction(list);
        assertEquals(expected, actual, "Wrong conversion for balance operation");
    }

    @Test
    void convertToTransaction_supplyItemOnList_ok() {
        List<String> list = new ArrayList<>();
        list.add("s,banana,100");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        List<FruitTransaction> actual = dataConverter.convertToTransaction(list);
        assertEquals(expected, actual, "Wrong conversion for supply operation");
    }

    @Test
    void convertToTransaction_purchaseItemOnList_ok() {
        List<String> list = new ArrayList<>();
        list.add("p,banana,13");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        List<FruitTransaction> actual = dataConverter.convertToTransaction(list);
        assertEquals(expected, actual, "Wrong conversion for purchase operation");
    }

    @Test
    void convertToTransaction_returnItemOnList_ok() {
        List<String> list = new ArrayList<>();
        list.add("r,banana,50");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 50));
        List<FruitTransaction> actual = dataConverter.convertToTransaction(list);
        assertEquals(expected, actual, "Wrong conversion for return operation");
    }

    @Test
    void convertToTransaction_properListVariousOperations_ok() {
        List<String> list = new ArrayList<>();
        list.add("b,banana,20");
        list.add("s,banana,100");
        list.add("p,banana,13");
        list.add("r,banana,50");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 50));
        assertEquals(expected, dataConverter.convertToTransaction(list),
                "Wrong conversion for various elements");
    }

    @Test
    void convertToTransaction_wrongDataListNotEnoughInfo_ok() {
        List<String> list = new ArrayList<>();
        list.add("b,20");
        list.add("banana,100");
        list.add("13");
        list.add("");
        List<FruitTransaction> expected = new ArrayList<>();
        assertEquals(expected, dataConverter.convertToTransaction(list),
                "Wrong conversion if not enough information");
    }

    @Test
    void convertToTransaction_wrongDataListTooMuchInfo_ok() {
        List<String> list = new ArrayList<>();
        list.add("b,banana,20,rpm");
        list.add("s,banana,100,100,200");
        list.add("p,banana,13,potato,p");
        list.add("s,banana,50,p,banana,70");
        List<FruitTransaction> expected = new ArrayList<>();
        assertEquals(expected, dataConverter.convertToTransaction(list),
                "Wrong conversion if too much information");
    }

    @Test
    void convertToTransaction_someGoodSomeWrongDataList_ok() {
        List<String> list = new ArrayList<>();
        list.add("b,20");
        list.add("banana,100");
        list.add("p,banana,13,potato,p");
        list.add("s,banana,50,p,banana,70");
        list.add("b,banana,20");
        list.add("s,banana,100");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        assertEquals(expected, dataConverter.convertToTransaction(list),
                "Wrong conversion if some good some wrong information");
    }

    @Test
    void convertToTransaction_wrongDataFormatList_ok() {
        List<String> list = new ArrayList<>();
        list.add("b:banana:20");
        list.add("s;banana;100");
        List<FruitTransaction> expected = new ArrayList<>();
        assertEquals(expected, dataConverter.convertToTransaction(list),
                "Wrong conversion if no coma separators");
    }
}
