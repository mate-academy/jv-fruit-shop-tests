package core.basesyntax.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import core.basesyntax.service.StringTransformator;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class StringTransformatorImplTest {
    private static StringTransformator transformator;
    private List<String> testListOfStrings;

    @BeforeClass
    public static void beforeClass() {
        transformator = new StringTransformatorImpl();
    }

    @Test
    public void transformStrings_NotNullList_Ok() {
        testListOfStrings = List.of("b,orange,0", "s,orange,50", "p,orange,20", "b,banana,70");
        List<String[]> outputListOfArrays = transformator.transformStrings(testListOfStrings);
        assertNotNull(outputListOfArrays);
    }

    @Test(expected = RuntimeException.class)
    public void transformStrings_NullList_notOk() {
        testListOfStrings = null;
        assertNull(transformator.transformStrings(testListOfStrings));
    }
}
