package core.basesyntax.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FruitShopController {
    private static final String REPORT_FILE = "src/main/resources/finalReport.csv";

    public void run() {
        try {
            Path reportPath = Path.of(REPORT_FILE);

            // Tworzy plik, jeśli nie istnieje
            if (!Files.exists(reportPath)) {
                Files.createDirectories(reportPath.getParent());
                Files.createFile(reportPath);
            }

            // Zapisuje przykładowe dane do pliku (możesz zmienić na rzeczywisty raport)
            String reportData = "Fruit,Quantity\nApple,10\nBanana,5\n";
            Files.writeString(reportPath, reportData, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Raport został wygenerowany: " + REPORT_FILE);
        } catch (IOException e) {
            throw new RuntimeException("Błąd podczas generowania raportu", e);
        }
    }
}
