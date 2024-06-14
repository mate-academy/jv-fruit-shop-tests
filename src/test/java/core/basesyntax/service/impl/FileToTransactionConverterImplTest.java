package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.CantWorkWithThisFileException;
import core.basesyntax.service.FileToTransactionConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileToTransactionConverterImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private FileToTransactionConverter converter;

    @BeforeEach
    void setUp() {
        converter = new FileToTransactionConverterImpl();
    }

    @Test
    void convert_EmptyList_NotOk() {
        List<String> list = new ArrayList<>();
        assertThrows(CantWorkWithThisFileException.class,
                () -> converter.convert(list),
                "Can't convert empty list");
    }

    @Test
    void convert_FruitTransactionEqualsInformationFromLines_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("Title");
        lines.add("b,banana,20");
        lines.add("b,apple,100");
        lines.add("s,banana,100");
        lines.add("p,banana,13");
        lines.add("r,apple,10");
        lines.add("p,apple,20");
        lines.add("p,banana,5");
        lines.add("s,banana,50");

        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(
                Operation.BALANCE,BANANA, 20));
        fruitTransactions.add(new FruitTransaction(
                Operation.BALANCE,APPLE, 100));
        fruitTransactions.add(new FruitTransaction(
                Operation.SUPPLY,BANANA, 100));
        fruitTransactions.add(new FruitTransaction(
                Operation.PURCHASE,BANANA, 13));
        fruitTransactions.add(new FruitTransaction(
                Operation.RETURN,APPLE, 10));
        fruitTransactions.add(new FruitTransaction(
                Operation.PURCHASE,APPLE, 20));
        fruitTransactions.add(new FruitTransaction(
                Operation.PURCHASE,BANANA, 5));
        fruitTransactions.add(new FruitTransaction(
                Operation.SUPPLY,BANANA, 50));

        List<FruitTransaction> result = converter.convert(lines);

        for (int i = 0; i < fruitTransactions.size(); i++) {
            assertEquals(fruitTransactions.get(i).getFruit(),
                    result.get(i).getFruit(),"Fruits are equal");
            assertEquals(fruitTransactions.get(i).getQuantity(),
                    result.get(i).getQuantity(),"Quantities is equal");
            assertEquals(fruitTransactions.get(i).getOperation(),
                    result.get(i).getOperation(),"Operations is equal");
        }
    }
}
