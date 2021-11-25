package core.basesyntax.service.impl;

import core.basesyntax.service.Parser;
import core.basesyntax.service.Validator;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;
    private static Parser parser;

    @BeforeClass
    public void initialization() {
        validator = new ValidatorImpl();
        parser = new ParserImpl(null);
    }

    @Rule

    @Test
    public void parse_ValidatorIsNull() {

    }

}