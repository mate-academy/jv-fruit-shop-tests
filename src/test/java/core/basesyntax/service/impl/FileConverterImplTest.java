package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileConverterImplTest {
    private static FileConverter fileConverter;
    private static List<String> listWithValidValues;
    private static List<String> listWithInvalidOperations;
    private static List<String> listWithInvalidInputData;
    private static List<String> listWithoutOperation;
    private static List<String> listWithoutCommaSeparator;
    private static List<String> nullList;

    @BeforeAll
    static void beforeAll() {
        fileConverter = new FileConverterImpl();
        listWithValidValues = getListWithValidValues();
        listWithInvalidOperations = getListWithInvalidOperations();
        listWithInvalidInputData = getListWithInvalidInputData();
        listWithoutOperation = getListWithoutOperation();
        listWithoutCommaSeparator = getListWithoutCommaSeparator();
    }

    @Test
    void convertToObjects_inputCorrectList_Ok() {
        List<FruitTransaction> actualObjects = fileConverter.convertToObjects(listWithValidValues);
        List<FruitTransaction> expectedObjects = getCorrectObjects();
        assertEquals(expectedObjects, actualObjects);
    }

    @Test
    void convertToObjects_inputListWithInvalidOperations_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fileConverter.convertToObjects(listWithInvalidOperations));
    }

    @Test
    void convertToObjects_inputNullList_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fileConverter.convertToObjects(nullList));
    }

    @Test
    void convertToObjects_inputListWithInvalidValues_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                fileConverter.convertToObjects(listWithInvalidInputData));
    }

    @Test
    void convertToObjects_inputListWithoutOperations_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                fileConverter.convertToObjects(listWithoutOperation));
    }

    @Test
    void convertToObjects_inputListWithoutCommaSep_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                fileConverter.convertToObjects(listWithoutCommaSeparator));
    }

    private static List<String> getListWithValidValues() {
        return List.of(
                "Operation,Type,Quantity",
                "b,banana,20",
                "s,apple,100",
                "s,banana,100"
        );
    }

    private static List<String> getListWithoutCommaSeparator() {
        return List.of(
                "OperationTypeQuantity",
                "bbanana20",
                "sapple100",
                "sbanana100"
        );
    }

    private static List<String> getListWithInvalidInputData() {
        return List.of(
                "Operation,OparationTwo,Type,Quantity",
                "p,b,banana,20",
                "s,b,apple,100",
                "p,s,banana,100"
        );
    }

    private static List<String> getListWithoutOperation() {
        return List.of(
                "Type,Quantity,",
                "banana,20",
                "apple,100",
                "banana,100"
        );
    }

    private static List<String> getListWithInvalidOperations() {
        return List.of(
                "Operation,Type,Quantity",
                "bal,banana,20",
                "supply,apple,30",
                "PR,banana,10"
        );
    }

    private static List<FruitTransaction> getCorrectObjects() {
        FruitTransaction fruitTransactionOne = new FruitTransaction();
        fruitTransactionOne.setFruit("banana");
        fruitTransactionOne.setQuantity(20);
        fruitTransactionOne.setOperation(FruitTransaction.Operation.BALANCE);

        FruitTransaction fruitTransactionTwo = new FruitTransaction();
        fruitTransactionTwo.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransactionTwo.setFruit("apple");
        fruitTransactionTwo.setQuantity(100);

        FruitTransaction fruitTransactionThree = new FruitTransaction();
        fruitTransactionThree.setQuantity(100);
        fruitTransactionThree.setFruit("banana");
        fruitTransactionThree.setOperation(FruitTransaction.Operation.SUPPLY);

        return List.of(
                fruitTransactionOne,
                fruitTransactionTwo,
                fruitTransactionThree
        );
    }
}
