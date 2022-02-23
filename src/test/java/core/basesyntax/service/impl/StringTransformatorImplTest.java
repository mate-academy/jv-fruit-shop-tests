package core.basesyntax.service.impl;

import core.basesyntax.service.StringTransformator;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class StringTransformatorImplTest {
    private List<String> testListOfStrings = List.of("b,orange,0", "s,orange,50", "p,orange,20", "b,banana,70");
    private final StringTransformator transformator = new StringTransformatorImpl();

    @Test
    public void transformNotNullList() {
        testListOfStrings = List.of("b,orange,0", "s,orange,50", "p,orange,20", "b,banana,70");
        List<String[]> outputListOfArrays = transformator.transformStrings(testListOfStrings);
        assertNotNull(outputListOfArrays);
    }

    @Test(expected = RuntimeException.class)
    public void transformNullList() {
        testListOfStrings = null;
        assertNull(transformator.transformStrings(testListOfStrings));
    }
}
