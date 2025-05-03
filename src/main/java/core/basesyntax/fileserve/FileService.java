package core.basesyntax.fileserve;

public interface FileService {
    String getFruitData(String fromFile);

    boolean writeToFile(String fruitReport, String toFile);
}
