package core.basesyntax.activity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ActivityParserImplTest {
    private final ActivityParserImpl activityParser = new ActivityParserImpl();

    @Test
    public void parseActivity_balanceAction_ok() {

        Activities expected = Activities.BALANCE;
        Activities actual = activityParser.parseActivity("b,fruit,2");
        assertEquals(expected, actual);
    }

    @Test
    public void parseActivity_supplyAction_ok() {
        Activities expected = Activities.SUPPLY;
        Activities actual = activityParser.parseActivity("s,fruit,2");
        assertEquals(expected, actual);
    }

    @Test
    public void parseActivity_purchaseAction_ok() {
        Activities expected = Activities.PURCHASE;
        Activities actual = activityParser.parseActivity("p,fruit,2");
        assertEquals(expected, actual);
    }

    @Test
    public void parseActivity_returnActionWithBlank_ok() {
        Activities expected = Activities.RETURN;
        Activities actual = activityParser.parseActivity("r   ,fruit,2");
        assertEquals(expected, actual);
    }
}
