package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.DataConverter;
import core.basesyntax.FileReader;
import core.basesyntax.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String CSV_INPUT
            = "src/main/java/core/basesyntax/resources/reportToRead.csv";
    private static DataConverter dataConverter;
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test
    void convertToTransaction_Null_Input_NotOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(null),
                "Input data is null");
    }

    @Test
    void convertToTransaction_EmptyListInput_NotOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(List.of()),
                "Input list is empty");
    }

    @Test
    void convertToTransaction_Ok() {
        List<FruitTransaction> expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));

        assertEquals(expectedList, dataConverter.convertToTransaction(fileReader.read(CSV_INPUT)));
    }
}
