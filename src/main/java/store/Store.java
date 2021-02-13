package store;

public interface Store {
    void saveData(String[] lines);
    String[] loadData();
}
