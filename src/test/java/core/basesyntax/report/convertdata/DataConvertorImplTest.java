package core.basesyntax.report.convertdata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitOperation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConvertorImplTest {
    private DataConvertorImpl dataConvertor;

    @BeforeEach
    void setUp() {
        dataConvertor = new DataConvertorImpl();
    }

    @Test
    void testConvertToTransactionValidData() {
        List<String> fruitInfoList = Arrays.asList(
                "operation,fruit,quantity",
                "b,apple,10",
                "s,banana,20",
                "p,orange,5"
        );

        List<FruitOperation> result = dataConvertor.convertToTransaction(fruitInfoList);

        assertEquals(3, result.size());

        assertEquals(FruitOperation.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(10, result.get(0).getQuantity());

        assertEquals(FruitOperation.Operation.SUPPLY, result.get(1).getOperation());
        assertEquals("banana", result.get(1).getFruit());
        assertEquals(20, result.get(1).getQuantity());

        assertEquals(FruitOperation.Operation.PURCHASE, result.get(2).getOperation());
        assertEquals("orange", result.get(2).getFruit());
        assertEquals(5, result.get(2).getQuantity());
    }

    @Test
    void testConvertToTransactionWithInvalidData() {
        List<String> fruitInfoList = Arrays.asList(
                "operation,fruit,quantity",
                "b,apple,10",
                "x,banana,-20",
                ",orange,5",
                "r,kiwi,abc"
        );

        List<FruitOperation> result = dataConvertor.convertToTransaction(fruitInfoList);

        assertEquals(2, result.size());

        assertEquals(FruitOperation.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(10, result.get(0).getQuantity());

        assertEquals(FruitOperation.Operation.RETURN, result.get(1).getOperation());
        assertEquals("kiwi", result.get(1).getFruit());
        assertEquals(0, result.get(1).getQuantity());
    }

    @Test
    void testConvertToTransactionWithEmptyList() {
        List<String> fruitInfoList = Collections.emptyList();

        List<FruitOperation> result = dataConvertor.convertToTransaction(fruitInfoList);

        assertEquals(0, result.size());
    }

    @Test
    void testConvertToTransactionWithInvalidCsvFormat() {
        List<String> fruitInfoList = Arrays.asList(
                "operation,fruit,quantity",
                "b,apple",
                "s,banana,20",
                "p"
        );

        List<FruitOperation> result = dataConvertor.convertToTransaction(fruitInfoList);

        assertEquals(1, result.size());

        assertEquals(FruitOperation.Operation.SUPPLY, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(20, result.get(0).getQuantity());
    }

    @Test
    void testConvertToTransactionWithAllValid() {
        List<String> fruitInfoList = Arrays.asList(
                "operation,fruit,quantity",
                "b,grape,50",
                "s,apple,100"
        );

        List<FruitOperation> result = dataConvertor.convertToTransaction(fruitInfoList);

        assertEquals(2, result.size());

        assertEquals(FruitOperation.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("grape", result.get(0).getFruit());
        assertEquals(50, result.get(0).getQuantity());

        assertEquals(FruitOperation.Operation.SUPPLY, result.get(1).getOperation());
        assertEquals("apple", result.get(1).getFruit());
        assertEquals(100, result.get(1).getQuantity());
    }
}
