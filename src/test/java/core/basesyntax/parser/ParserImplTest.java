package core.basesyntax.parser;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.model.OperationType;

import static org.junit.Assert.assertEquals;

import core.basesyntax.validator.ValidatorImpl;
import core.basesyntax.validator.length.LineLengthValidatorImpl;
import core.basesyntax.validator.quantity.QuantityValidatorImpl;
import core.basesyntax.validator.type.TypeValidatorImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParserImplTest {
    private static Parser parser;
    private static List<String> linesContent;
    private static final String APPLE = "apple";
    private static final String BALANCE = "b";
    private static final long QUANTITY  = 234;
    @BeforeClass
    public static void setUp() {
        parser = new ParserImpl(new ValidatorImpl(new LineLengthValidatorImpl(),
                new TypeValidatorImpl(), new QuantityValidatorImpl()));
        linesContent = new ArrayList<>();
    }

    @Test
    public void parseLines() {
        linesContent.add("type,fruit,quantity");
        linesContent.add(BALANCE + ',' + APPLE + ',' + QUANTITY);
        List<FruitRecord> fruitRecords = new ArrayList<>();
        fruitRecords.add(new FruitRecord(new Fruit(APPLE, QUANTITY), OperationType.B));
        assertEquals(fruitRecords, parser.parseLines(linesContent));
    }
}