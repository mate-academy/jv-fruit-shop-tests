package writer;

@FunctionalInterface
public interface Writer<K, J> {
    void write(K writeTo, J data);
}
