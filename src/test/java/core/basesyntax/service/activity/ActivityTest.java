package core.basesyntax.service.activity;

import junit.framework.TestCase;

public class ActivityTest extends TestCase {
    private Activity activity;

    public void testBalanceActivity_Ok() {
        activity = new BalanceActivity();
        int actual = this.activity.getActivity(10);
        int expected = 10;
        assertEquals(expected, actual);
    }

    public void testPurchaseActivity_Ok() {
        activity = new PurchaseActivity();
        int actual = this.activity.getActivity(20);
        int expected = -20;
        assertEquals(expected, actual);
    }

    public void testReturnActivity_Ok() {
        activity = new ReturnActivity();
        int actual = this.activity.getActivity(30);
        int expected = 30;
        assertEquals(expected, actual);
    }

    public void testSupplyActivity_Ok() {
        activity = new SupplyActivity();
        int actual = this.activity.getActivity(40);
        int expected = 40;
        assertEquals(expected, actual);
    }
}
