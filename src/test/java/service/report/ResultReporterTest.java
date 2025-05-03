package service.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dao.Dao;
import dao.StorageDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResultReporterTest {
    private static final String HEAD_TEXT = "fruit,quantity";
    private static Dao dao;
    private static Reporter reporter;

    @BeforeAll
    static void beforeAll() {
        dao = new StorageDao();
        reporter = new ResultReporter(dao);
    }

    @BeforeEach
    void beforeEach() {
        dao.updateStock(new HashMap<>());
    }

    @Test
    void validCase_fullStorage() {
        Map<String, Integer> map = new HashMap<>();
        map.put("banana", 120);
        map.put("apple", 110);
        dao.updateStock(map);
        List<String> expected = List.of(HEAD_TEXT, "banana,120", "apple,110");
        List<String> actual = reporter.generate();
        assertEquals(expected, actual);
    }

    @Test
    void validCase_emptyStorage() {
        List<String> expected = List.of(HEAD_TEXT);
        List<String> actual = reporter.generate();
        assertEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        dao.updateStock(new HashMap<>());
    }
}
