package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ActivityTest {
    private static final Activity.Builder ACTIVITY_BUILDER = new Activity.Builder();
    private static final Operation TEST_OPERATION = Operation.BALANCE;
    private static final String TEST_ITEM = "banana";
    private static final String EMPTY_STRING = "";
    private static final int TEST_QUANTITY = 10;
    private static final int NEGATIVE_VALUE = -1;
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Activity testActivity;

    @Before
    public void setUp() {
        testActivity = ACTIVITY_BUILDER.setOperation(TEST_OPERATION)
                .setItem(TEST_ITEM)
                .setQuantity(TEST_QUANTITY)
                .build();
    }

    @Test
    public void getOperation_Ok() {
        assertEquals(TEST_OPERATION, testActivity.getOperation());
    }

    @Test
    public void getItem_Ok() {
        assertEquals(TEST_ITEM, testActivity.getItem());
    }

    @Test
    public void getQuantity_Ok() {
        assertEquals(TEST_QUANTITY, testActivity.getQuantity());
    }

    @Test
    public void setNegativeQuantity_NotOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Incorrect value of quantity");
        ACTIVITY_BUILDER.setQuantity(NEGATIVE_VALUE).build();
    }

    @Test
    public void setEmptyItem_NotOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Incorrect name of item");
        ACTIVITY_BUILDER.setItem(EMPTY_STRING);
    }
}
