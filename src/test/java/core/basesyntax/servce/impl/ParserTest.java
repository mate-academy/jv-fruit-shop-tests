package core.basesyntax.servce.impl;

import core.basesyntax.servce.CsvParser;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {
    private static CsvParser parser;

    @BeforeClass
    public static void setUp() {
        parser = new Parser();
    }

    @Test
    public void parse_NullInput_Ok() {

    }

    @Test
    public void parse_EmptyListInput_Ok() {

    }

    @Test
    public void parse_EmptyElementOfList_Ok() {

    }

    @Test
    public void parse_OneFruitManyMovements_Ok() {

    }

    @Test
    public void parse_ManyFruitsManyMovements_Ok() {

    }

}