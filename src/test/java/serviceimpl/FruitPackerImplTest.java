package serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitPackerImplTest {
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private static final int TEST_NUMBER_75 = 75;
    private static final int TEST_NUMBER_54 = 54;
    private static final String NOT_NULL_MESSAGE = "Null can not be added";
    private final List<FruitTransaction> transactionList = new ArrayList<>();
    private final FruitPackerImpl fruitPacker = new FruitPackerImpl();

    @BeforeEach
    void setUp() {
        transactionList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE, APPLE, TEST_NUMBER_75));
        transactionList.add(new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, ORANGE, TEST_NUMBER_54));
    }

    @AfterEach
    void tearDown() {
        transactionList.clear();
    }

    @Test
    public void parseList_validData_ok() {
        List<String> transactionsStringList = transactionList
                .stream()
                .map(t -> t.getOperation().getOperation()
                        + FruitPackerImpl.COMMA
                        + t.getFruit()
                        + FruitPackerImpl.COMMA
                        + t.getQuantity())
                .collect(Collectors.toList());
        assertEquals(transactionList,
                fruitPacker.makeList(transactionsStringList));
    }

    @Test
    void makeList_nullInputList_notOk() {
        assertThrows(RuntimeException.class, () ->
                fruitPacker.makeList(null), NOT_NULL_MESSAGE);
    }
}

