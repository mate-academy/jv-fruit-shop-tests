package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private final ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();

    @Test
    void getReport_validStorage_containsExpectedData() {
        // Заполняем хранилище тестовыми данными
        Storage.modifyFruitStorage("apple", 100);
        Storage.modifyFruitStorage("banana", 50);

        // Генерируем отчет
        String report = reportGenerator.getReport();

        // Ожидаемые строки в отчете
        List<String> expectedLines = List.of(
                "fruit,quantity", // Заголовок
                "apple,100",
                "banana,50"
        );

        // Преобразуем отчет в список строк
        List<String> reportLines = report.lines().collect(Collectors.toList());

        // Проверяем, что все ожидаемые строки присутствуют в отчете
        for (String expectedLine : expectedLines) {
            assertTrue(reportLines.contains(expectedLine),
                    "Отчет не содержит строку: " + expectedLine);
        }
    }
}
