package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReportImplTest {
    private final Report report = new ReportImpl();

    @Test(expected = RuntimeException.class)
      public void reportwrongData_NotOk() {
        List<String> wrongDataList = new ArrayList<>();
        wrongDataList.add("Hello");
        wrongDataList.add("wrong,banana,0");
        report.report(wrongDataList);
    }

    @Test
    public void reportCorrect_Ok() {
        List<String> correct = new ArrayList<>();
        correct.add("Hello");
        correct.add("b,banana,100");
        correct.add("b,apple,2");
        String expected = "fruit,quantity\nbanana,100" + "\napple,2\n";
        String actual = report.report(correct);
        assertEquals(expected, actual);
    }
}
