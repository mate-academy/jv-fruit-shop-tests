package core.basesyntax.service.implementations;

import core.basesyntax.service.inerfaces.WriteService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteServiceImpl implements WriteService {
    public void writeToFile(String filePath, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("fruit,quantity" + System.lineSeparator() + data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + filePath, e);
        }
    }
}
