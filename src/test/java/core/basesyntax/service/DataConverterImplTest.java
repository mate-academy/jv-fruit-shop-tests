package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private List<String> dataList = new ArrayList<>();

    private FruitTransaction firstFruitTransaction = new FruitTransaction();
    private FruitTransaction secondFruitTransaction = new FruitTransaction();

    private final DataConverter dataConverter = new DataConverterImpl();

    @BeforeEach
    void setLists() {
        dataList.add("b,banana,20");
        dataList.add("b,apple,100");

        firstFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstFruitTransaction.setFruit("banana");
        firstFruitTransaction.setQuantity(20);

        secondFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        secondFruitTransaction.setFruit("apple");
        secondFruitTransaction.setQuantity(100);
    }

    @Test
    void dataConvert_Ok() {
        List<FruitTransaction> fruitTransactions = dataConverter.convertToTransaction(dataList);
        assertEquals(firstFruitTransaction, fruitTransactions.get(0));
        assertEquals(secondFruitTransaction, fruitTransactions.get(1));
    }

}
