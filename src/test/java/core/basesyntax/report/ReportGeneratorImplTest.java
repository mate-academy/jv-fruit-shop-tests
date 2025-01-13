package core.basesyntax.report;

import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportGeneratorImplTest {
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @Test
    void getReport_validInventory_ok() {
        Map<String, Integer> inventory = Map.of(
                "babana", 30, "apple", 50);
        // Викликається метод getReport з переданим
        // інвентарем, і результат зберігається у змінній result.
        String result = reportGenerator.getReport(inventory);
        // Перевіряється, що результат відповідає очікуваному формату:
        // Кожен елемент інвентарю у форматі fruit,quantity.
        // Дані розділені символом нового рядка.
        assertEquals("apple,50\nbanana,30\n", result);
    }

    @Test
    void getReport_emptyInventory_returnsEmptyString() {
        // Передається порожній інвентар (Map.of()),
        // і результат зберігається у змінній result
        String result = reportGenerator.getReport(Map.of());
        // Перевіряється, що результат — порожній рядок.
        assertEquals("", result);
    }
}
