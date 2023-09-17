package fruitshop.sevice.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.model.FruitTransaction;
import fruitshop.sevice.DataProcessorService;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataProcessorServiceImplTest {
    private DataProcessorService dataProcessorService = new DataProcessorServiceImpl();

    @Test
    void processData_quantityLessThanZero_notOk() {
        List<String> data = List.of("b,banana,10", "b,apple,10", "q,banana,20");
        assertThrows(RuntimeException.class,
                () -> dataProcessorService.processInputData(data));
    }

    @Test
    void processData_invalidOperation_notOk() {
        List<String> data = List.of("b,banana,10", "b,apple,-10", "s,banana,20");
        assertThrows(RuntimeException.class,
                () -> dataProcessorService.processInputData(data));
    }

    @Test
    void processData_notNumericQuantity_notOk() {
        List<String> data = List.of("b,banana,10", "b,apple,10", "s,banana,2a0");
        assertThrows(RuntimeException.class,
                () -> dataProcessorService.processInputData(data));
    }

    @Test
    void processData_validData_ok() {
        List<String> data = List.of("b,banana,10", "b,apple,10", "s,banana,20");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20)
        );
        List<FruitTransaction> processedData = dataProcessorService.processInputData(data);
        assertEquals(expected, processedData);

    }
}
