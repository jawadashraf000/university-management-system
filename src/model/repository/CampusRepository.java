package model.repository;

import model.base.CampusEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Generic class made to manage collections of Different classes i.e., Facility, Student.

public class CampusRepository<T extends CampusEntity> implements Serializable {

    private String repositoryName;
    private List<T> items = new ArrayList<>();

    public CampusRepository(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public void add(T item) {
        if (findById(item.getEntityID()) != null) {
            System.out.println("ID already exists: " + item.getEntityID());
            return;
        }
        items.add(item);
    }

    public boolean remove(String entityID) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getEntityID().equals(entityID)) {
                items.remove(i);
                return true;
            }
        }

        return false;
    }

    public T findById(String entityID) {
        for (T item : items)
            if (item.getEntityID().equals(entityID)) return item;
        return null;
    }

    public List<T> getAll() { return new ArrayList<>(items); }

    public List<T> searchByName(String keyword) {
        List<T> result = new ArrayList<>();
        for (T item : items)
            if (item.getName().toLowerCase().contains(keyword.toLowerCase()))
                result.add(item);
        return result;
    }

    public int size() { return items.size(); }
    public boolean isEmpty() { return items.isEmpty(); }

    public double getTotalOperationalCost() {
        double total = 0;
        for (T item : items) {
            total += item.calculateOperationalCost();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Repository[" + repositoryName + "] | Items: " + items.size() +
               " | Total Cost: Rs." + String.format("%.2f", getTotalOperationalCost());
    }
}
