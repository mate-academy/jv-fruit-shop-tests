package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InputConverterServiceTest {

    private static InputConverterService converterService;

    @BeforeAll
    static void beforeAll() {
        converterService = new InputConverterService();
    }

    @Test
    void convertToTransactions_Balance_Ok() {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"type", "fruit", "quantity"});
        data.add(new String[]{"b", "test1", "15"});
        List<FruitTransaction> result = converterService.convertToTransactions(data);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "test1", 15));
        assertEquals(expected, result);
    }

    @Test
    void convertToTransactions_Supply_Ok() {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"type", "fruit", "quantity"});
        data.add(new String[]{"s", "test1", "10"});
        List<FruitTransaction> result = converterService.convertToTransactions(data);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "test1", 10));
        assertEquals(expected, result);
    }

    @Test
    void convertToTransactions_Purchase_Ok() {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"type", "fruit", "quantity"});
        data.add(new String[]{"p", "test1", "5"});
        List<FruitTransaction> result = converterService.convertToTransactions(data);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "test1", 5));
        assertEquals(expected, result);
    }

    @Test
    void convertToTransactions_Return_Ok() {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"type", "fruit", "quantity"});
        data.add(new String[]{"r", "test1", "2"});
        List<FruitTransaction> result = converterService.convertToTransactions(data);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "test1", 2));
        assertEquals(expected, result);
    }
}
