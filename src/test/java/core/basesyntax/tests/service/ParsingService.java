package core.basesyntax.tests.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ParsingServiceImpl;
import org.junit.Test;

public class ParsingService {
    private core.basesyntax.service.ParsingService parsingService = new ParsingServiceImpl();

    @Test
    public void parsingService_CorrectData_Ok() {
        String line = "b,banana,20";
        String expected = "FruitTransaction{operation=BALANCE, fruit='banana', quantity=20}";
        assertEquals(parsingService.parse(line).toString(), expected);
    }

    @Test (expected = RuntimeException.class)
    public void parsingService_IncorrectData_NotOk() {
        String line = "03,213,one";
        parsingService.parse(line);
    }

    @Test (expected = RuntimeException.class)
    public void parsingService_Null_NotOk() {
        parsingService.parse(null);
    }

    @Test (expected = RuntimeException.class)
    public void parsingService_Empty_NotOk() {
        String line = "";
        parsingService.parse(line);
    }
}
