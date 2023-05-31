package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ParseFileImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseFileImplTest {
    private static final String HEAD_LINE = "type,fruit,quantity";
    private static final String SECOND_LINE = "p,apple,30";
    private static final String CURRENT_VALUE = "p,apple,30";
    private static ParseFile parser;
    private static List<String> EMPTY_LIST;
    private static List<String> dataList;
    private static List<String> current;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParseFileImpl();
        EMPTY_LIST = new ArrayList<>();
        dataList = new ArrayList<>();
        dataList.add(HEAD_LINE);
        dataList.add(SECOND_LINE);
        current = new ArrayList<>();
    }

    @After
    public void tearDown() {
        EMPTY_LIST.clear();
    }

    @Test(expected = RuntimeException.class)
    public void parseEmptyList_notOk() {
        parser.parseData(EMPTY_LIST);
    }

    @Test
    public void parseExistList_ok() {
        current.add(CURRENT_VALUE);
        parser.parseData(dataList);
        assertEquals(dataList, current);
    }
}
