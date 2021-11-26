package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    public static final TransactionDto VALID_BALANCE_DTO =
            new TransactionDto("b", "apple", 20);
    public static final TransactionDto VALID_ADD_DTO =
            new TransactionDto("s", "apple", 100);
    public static final Integer BALANCE_QUANTITY = 20;
    public static final Integer ADD_QUANTITY = 120;
    private static OperationHandler addOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        addOperationHandler = new AddOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.clear();
    }

    @Test
    public void correctBalanceQuantity_Ok() {
        addOperationHandler.apply(VALID_BALANCE_DTO);
        Fruit fruit = new Fruit(VALID_BALANCE_DTO.getFruitName());
        Integer actual = Storage.storage.get(fruit);
        assertEquals(BALANCE_QUANTITY,
                actual);
    }

    @Test
    public void correctAddQuantity_Ok() {
        Fruit fruit = new Fruit(VALID_ADD_DTO.getFruitName());
        Storage.storage.put(fruit, BALANCE_QUANTITY);

        addOperationHandler.apply(VALID_ADD_DTO);
        Integer actual = Storage.storage.get(fruit);
        assertEquals(ADD_QUANTITY,
                actual);
    }
}
