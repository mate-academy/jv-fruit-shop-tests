package core.basesyntax.service;

import core.basesyntax.data.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitMapperImplTest {
    private static final int ONE = 1;
    private static final int QUANTITY_BANANA_BALANCE = 20;
    private static final int ZERO_INDEX = 0;
    private static final String CODE_OPERATION_BALANCE = "b";
    private static final String HEAD_INPUT = "type,fruit,quantity";
    private static final String FIRST_INVALID_ROW = "b,null,20";
    private static final String FIRST_VALID_ROW = "b,banana,20";
    private static final String FRUIT_BANANA = "banana";
    private static final String SECOND_VALID_ROW = "b,apple,100";
    private FruitMapper mapper;
    private List<String> lines;

    @BeforeEach
    void clear() {
        mapper = new FruitMapperImpl();
        lines = new ArrayList<>();
        lines.clear();
    }

    @Test
    void validInput_checkSize_Ok() {
        lines.add(HEAD_INPUT);
        lines.add(FIRST_VALID_ROW);
        lines.add(SECOND_VALID_ROW);
        int expected = lines.size() - ONE;
        int actual = mapper.mapData(lines).size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void listIsNull_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> mapper.mapData(null));
    }

    @Test
    void listIsEmpty_NotOk() {
        lines.add(HEAD_INPUT);
        Assertions.assertThrows(RuntimeException.class, () -> mapper.mapData(lines));
    }

    @Test
    void listContainsHead_Ok() {
        lines.add(HEAD_INPUT);
        lines.add(FIRST_VALID_ROW);
        Assertions.assertTrue(lines.contains(HEAD_INPUT));
    }

    @Test
    void headAbsent_NotOk() {
        lines.add(FIRST_VALID_ROW);
        lines.add(SECOND_VALID_ROW);
        Assertions.assertThrows(IllegalArgumentException.class, () -> mapper.mapData(lines));
    }

    @Test
    void validInput_checkOperation_Ok() {
        lines.add(HEAD_INPUT);
        lines.add(FIRST_VALID_ROW);
        lines.add(SECOND_VALID_ROW);
        List<FruitTransaction> list = mapper.mapData(lines);
        Assertions.assertEquals(FruitTransaction.Operation
                .getOperationByCode(CODE_OPERATION_BALANCE), list.get(ZERO_INDEX).getOperation());
    }

    @Test
    void validInput_checkTypeFruit_Ok() {
        lines.add(HEAD_INPUT);
        lines.add(FIRST_VALID_ROW);
        lines.add(SECOND_VALID_ROW);
        List<FruitTransaction> list = mapper.mapData(lines);
        Assertions.assertEquals(FRUIT_BANANA, list.get(ZERO_INDEX).getFruit());
    }

    @Test
    void validInput_checkQuantity_Ok() {
        lines.add(HEAD_INPUT);
        lines.add(FIRST_VALID_ROW);
        lines.add(SECOND_VALID_ROW);
        List<FruitTransaction> list = mapper.mapData(lines);
        Assertions.assertEquals(QUANTITY_BANANA_BALANCE, list.get(ZERO_INDEX).getQuantity());
    }

    @Test
    void typeFruit_null_NotOk() {
        lines.add(HEAD_INPUT);
        lines.add(FIRST_INVALID_ROW);
        Assertions.assertThrows(IllegalArgumentException.class, () -> mapper.mapData(lines));
    }
}
