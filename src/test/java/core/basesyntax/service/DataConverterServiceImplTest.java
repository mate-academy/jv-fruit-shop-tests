package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidArraySplitLength;
import core.basesyntax.exception.UnknownOperationException;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterServiceImplTest {
    private static final List<String> DATA_LIST = List.of("type,fruit,quantity",
            "b,strawberry,50",
            "b,banana,40",
            "s,strawberry,15");

    private static final List<String> LINE_WITH_LINE_MORE_THAN_THREE = List.of(
            "type,fruit,quantity",
            "b,strawberry,50,10"
    );
    private static final List<String> LINE_WITH_LINE_LESS_THAN_THREE = List.of(
            "type,fruit,quantity",
            "b,strawberry"
    );
    private static final List<String> LINE_WITH_UNKNOWN_OPERATION = List.of(
            "type,fruit,quantity",
            "z,strawberry,50"
    );
    private static final List<String> LINE_WITH_NOT_NUMERICAL_VALUE = List.of(
            "type,fruit,quantity",
            "b,strawberry,ten"
    );
    private static final List<FruitTransaction> FRUIT_TRANSACTIONS_DATA = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "strawberry", 50),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 40),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "strawberry", 15));

    private DataConverterService dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterServiceImpl();
    }

    @Test
    void convert_isOk() {
        List<FruitTransaction> actual = dataConverter.convert(DATA_LIST);
        assertEquals(FRUIT_TRANSACTIONS_DATA, actual);
    }

    @Test
    void convert_nullValue_notOk() {
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convert(null));
    }

    @Test
    void convert_dataLengthMoreThanThree_notOk() {
        assertThrows(InvalidArraySplitLength.class,
                () -> dataConverter.convert(LINE_WITH_LINE_MORE_THAN_THREE));
    }

    @Test
    void convert_dataLengthLessThanThree_notOk() {
        assertThrows(InvalidArraySplitLength.class,
                () -> dataConverter.convert(LINE_WITH_LINE_LESS_THAN_THREE));
    }

    @Test
    void convert_unknownOperation_notOk() {
        assertThrows(UnknownOperationException.class,
                () -> dataConverter.convert(LINE_WITH_UNKNOWN_OPERATION));
    }

    @Test
    void convert_NotNumericalValue_notOK() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convert(LINE_WITH_NOT_NUMERICAL_VALUE));
    }
}
