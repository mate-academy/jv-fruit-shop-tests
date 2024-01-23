package core.basesyntax.service.filework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class AddDataToFileImpl implements AddDataToFile {

    @Override
    public void addInStorage(Map<String, Integer> fruitAndQuantity,String filePath) {
        File file = new File(filePath);
        StringBuilder builderFQ = new StringBuilder("fruit,quantity")
                .append(System.lineSeparator());
        for (Map.Entry<String, Integer> fruits : fruitAndQuantity.entrySet()) {
            builderFQ.append(fruits.getKey())
                    .append(",").append(fruits.getValue())
                    .append(System.lineSeparator());
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true))) {
                file.createNewFile();
                bufferedWriter.write(builderFQ.toString());
                builderFQ.delete(0,builderFQ.length());
            } catch (IOException e) {
                throw new RuntimeException("Sorry can't write data ",e);
            }
        }
    }
}
