package core.basesyntax.service;

import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ParserServiceTest {
    private static ParserService parserService;

    @Rule
    public ExpectedException exceptionRuleFileNotFound = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parser_InpuIstNullNotOK() {
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Input param is null.");
        parserService.parse(null);
    }

    @Test
    public void parser_InpuIsEmptyNotOK() {
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Input param is empty.");
        parserService.parse(List.of());
    }

    @Test
    public void parser_BadDataInFileNotOK() {
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Bad data in line: 2");
        parserService.parse(List.of("b,fruit,1", "b,,1"));
    }
}
