package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {

    @Override
    public List<String> readFromFileInput(String path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + path, e);
        }

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);

            String[] parts = line.split(",");
            if (parts.length != 3) {
                throw new RuntimeException("Invalid format on line " + (i + 1) + ": " + line);
            }

            String type = parts[0];
            String fruit = parts[1];
            String quantity = parts[2];

            if (!(type.equals("s") || type.equals("b") || type.equals("r") || type.equals("p"))) {
                throw new RuntimeException("Invalid type on line " + (i + 1) + ": " + type);
            }

            if (!(fruit.equals("apple") || fruit.equals("banana"))) {
                throw new RuntimeException("Invalid fruit on line " + (i + 1) + ": " + fruit);
            }

            try {
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt < 0) {
                    throw new RuntimeException("Quantity can't be negative on line "
                            + (i + 1) + ": " + quantity);
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid quantity on line " + (i + 1) + ": " + quantity);
            }
        }
        return lines; // Return the lines, excluding the header
    }

    @Override
    public List<String> readFromFileReport(String filepath) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + filepath, e);
        }
        String header = lines.get(0);
        if (!header.equalsIgnoreCase("fruit,quantity")) {
            throw new RuntimeException("Invalid header in file: " + header);
        }

        for (int i = 1; i < lines.size(); i++) { // Start from 1 to skip header
            String line = lines.get(i);

            String[] parts = line.split(",");
            if (parts.length != 2) {
                throw new RuntimeException("Invalid format on line " + (i + 1) + ": " + line);
            }

            String fruit = parts[0];
            String quantity = parts[1];

            if (!(fruit.equals("apple") || fruit.equals("banana"))) {
                throw new RuntimeException("Invalid fruit on line " + (i + 1) + ": " + fruit);
            }

            try {
                int qty = Integer.parseInt(quantity);
                if (qty <= 0) {
                    throw new RuntimeException("Quantity must be greater than 0 on line "
                            + (i + 1) + ": " + quantity);
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid quantity on line " + (i + 1) + ": " + quantity);
            }
        }
        return lines; // Return all the lines, including the header
    }

}
