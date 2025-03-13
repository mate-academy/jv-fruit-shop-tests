package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationsList;
import core.basesyntax.service.impl.DataFruitConverterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataFruitConverterTest {
    private List<String[]> data;

    @Test
    void convertToTransaction_Parse_NotOk() {
        data = new ArrayList<>();
        data.add(new String[]{"operation", "fruit", "quantity"});
        data.add(new String[]{"b", "apple", "ddd"});
        DataFruitConverterImpl dataFruitConverter = new DataFruitConverterImpl();
        assertThrows(RuntimeException.class, () -> {
            dataFruitConverter.convertToTransaction(data);
        });
    }

    @Test
    void convertToTransaction_Convertation_IsOk() {
        data = new ArrayList<>();
        data.add(new String[]{"operation", "fruit", "quantity"});
        data.add(new String[]{"s", "apple", "20"});
        DataFruitConverterImpl dataFruitConverter = new DataFruitConverterImpl();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setOperation(OperationsList.SUPPLY);
        fruitTransaction.setQuantity(20);
        List<FruitTransaction> createdList = dataFruitConverter.convertToTransaction(data);
        assertEquals(createdList.get(0).getFruit(), fruitTransaction.getFruit());
        assertEquals(createdList.get(0).getOperation(), fruitTransaction.getOperation());
        assertEquals(createdList.get(0).getQuantity(), fruitTransaction.getQuantity());
    }
}
