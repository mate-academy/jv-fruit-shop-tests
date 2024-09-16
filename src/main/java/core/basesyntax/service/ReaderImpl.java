package core.basesyntax.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderImpl implements Reader {

    @Override
    public List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        //System.out.println("Reading file from path: " + filePath);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            //System.out.println("File found, starting to read...");
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            //System.out.println("Error reading file: " +e.getMessage());
            throw new RuntimeException("Can't read data: " + filePath);
        }
        return lines;
    }
}
