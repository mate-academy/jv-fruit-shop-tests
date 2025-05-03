package core.basesyntax.service.impl;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ParserData;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserDataImplTest {
    private static ParserData parser;

    @BeforeClass
    public static void beforeClass() throws Exception {
        parser = new ParserDataImpl();
    }

    @Test
    public void parser_correctData_ok() {
        String data = "s,apple,3";
        Transaction expected = new Transaction("s",
                new Fruit("apple"),
                3);
        Transaction actual = parser.parseData(data);
        Assert.assertEquals(expected, actual);
    }
}
