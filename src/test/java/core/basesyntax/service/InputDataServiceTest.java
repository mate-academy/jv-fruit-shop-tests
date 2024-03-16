package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.impl.InputDataServiceImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InputDataServiceTest {
    private static final String HEADER = "type,fruit,quantity";
    private static InputDataService inputDataService;
    private static List<String> inputDataOk;
    private static List<String> inputDataNullField;
    private static List<FruitTransaction> expected;
    private static FruitTransaction fruitTransactionBanana;
    private static FruitTransaction fruitTransactionApple;

    @BeforeAll
    static void beforeAll() {
        inputDataService = new InputDataServiceImpl();
        inputDataOk = new ArrayList<>();
        inputDataNullField = new ArrayList<>();
        expected = new ArrayList<>();
        fruitTransactionBanana = new FruitTransaction();
        fruitTransactionApple = new FruitTransaction();
    }

    @Test
    void convertDataToObj_correctData_Ok() {
        inputDataOk = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100");

        fruitTransactionBanana.setFruit("banana");
        fruitTransactionBanana.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransactionBanana.setQuantity(20);

        fruitTransactionApple.setFruit("apple");
        fruitTransactionApple.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransactionApple.setQuantity(100);

        expected = List.of(fruitTransactionBanana, fruitTransactionApple);

        List<FruitTransaction> actual = inputDataService.convertDataToObj(inputDataOk);
        assertEquals(expected, actual);
    }

    @Test
    void convertDataToObj_NullField_NotOk() {
        inputDataNullField = List.of(
                "type,fruit,quantity",
                "null,banana,20",
                "b,apple,100");

        assertThrows(RuntimeException.class,
                () -> inputDataService.convertDataToObj(inputDataNullField),
                "\"Test failed! Exception should be thrown if input data is null"
        );
    }

    @Test
    void convertDataToObj_ListSize1000_Ok() {
        String dataField = "b,banana,20";
        ArrayList<String> list100Size = new ArrayList<>();
        list100Size.add(HEADER);
        for (int i = 0; i < 1000; i++) {
            list100Size.add(dataField);
        }
        List<FruitTransaction> actual = inputDataService.convertDataToObj(list100Size);

        assertEquals(1000, actual.size(), "The list should be 1000 in size");
    }

    @Test
    void convertDataToObj_ListSize0_Ok() {
        ArrayList<String> listSize0 = new ArrayList<>();
        listSize0.add(HEADER);
        List<FruitTransaction> actual = inputDataService.convertDataToObj(listSize0);
        assertEquals(0, actual.size(), "The list should be 0 in size");
    }

    @Test
    void convertDataToObj_QuantityIsChar_NotOk() {
        ArrayList<String> quantityCharList = new ArrayList<>();
        quantityCharList.add(HEADER);
        quantityCharList.add("b,100,banana");

        assertThrows(RuntimeException.class,
                () -> inputDataService.convertDataToObj(quantityCharList),
                "Test failed! Exception should be thrown if quantity case contains characters"
        );
    }
}
