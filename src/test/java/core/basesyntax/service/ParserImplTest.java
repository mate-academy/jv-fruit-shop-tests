package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.TransactionDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ParserImplTest {
    private Parser parser = new ParserImpl();
    private List<String> testList;

    @Test
    public void parser_validInput_ok() {
        testList = new ArrayList<>();
        testList.add("b,banana,20");
        testList.add("b,apple,100");
        List<TransactionDto> expected = new ArrayList<>();
        expected.add(new TransactionDto(TransactionDto.OperationTypes.BALANCE, "banana", 20));
        expected.add(new TransactionDto(TransactionDto.OperationTypes.BALANCE, "apple", 100));
        List<TransactionDto> actual = parser.parser(testList);
        assertEquals("Method should return " + expected + ", but was " + actual, expected, actual);
    }

    @Test
    public void parser_invalidInput_notOk() {
        testList = new ArrayList<>();
        testList.add("b,banana,20");
        testList.add("b,apple,aaa");
        assertThrows("Method should throw RuntimeExeption for invalid values",
                RuntimeException.class, () -> parser.parser(testList));
    }

    @Test
    public void parser_nullInput_notOk() {
        assertThrows("Method should throw RuntimeExeption for invalid values",
                RuntimeException.class, () -> parser.parser(null));
    }

    @Test
    public void parser_emptyInput_ok() {
        testList = new ArrayList<>();
        List<TransactionDto> expected = new ArrayList<>();
        List<TransactionDto> actual = parser.parser(testList);
        assertEquals("Method should return empty list for empty input", expected, actual);
    }
}
