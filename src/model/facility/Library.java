package model.facility;

import model.base.Reportable;
import java.util.ArrayList;
import java.util.List;

public class Library extends Facility implements Reportable {

    private int totalSeats;
    private int occupiedSeats;
    private List<Book> books = new ArrayList<>();

    public Library(String entityID, String name, String location,
                   int totalSeats, double maintenanceCost) {
        super(entityID, name, location, maintenanceCost, 2.0);
        this.totalSeats = totalSeats;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(String bookID) {
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            if (b.getBookID().equals(bookID)) {
                books.remove(b);
            }
        }
    }

    public Book findBook(String bookID) {
        for (Book b : books)
            if (b.getBookID().equals(bookID)) {
                return b;
            }
        return null;
    }

    public boolean checkoutBook(String bookID, String studentID) {
        Book b = findBook(bookID);
        if (b == null) return false;
        boolean ok = b.checkout(studentID);
        if (ok) recordVisit();
        return ok;
    }

    public boolean returnBook(String bookID) {
        Book b = findBook(bookID);
        if (b == null) return false;
        b.returnBook();
        return true;
    }

    public List<Book> getAvailableBooks() {
        List<Book> list = new ArrayList<>();
        for (Book b : books) {
            if (b.isAvailable()) {
                list.add(b);
            }
        }
        return list;
    }

    public List<Book> getBorrowedBooks() {
        List<Book> list = new ArrayList<>();
        for (Book b : books) {
            if (!b.isAvailable()) {
                list.add(b);
            }
        }
        return list;
    }

    @Override
    public String generateReport() {
        return "--- LIBRARY USAGE REPORT ---\n" +
                "Library     : " + getName() + "\n" +
                "Location    : " + getLocation() + "\n" +
                "Total Books : " + books.size() + "\n" +
                "Available   : " + getAvailableBooks().size() + "\n" +
                "Borrowed    : " + getBorrowedBooks().size() + "\n" +
                "Total Seats : " + totalSeats + "\n" +
                "Occupied    : " + occupiedSeats + "\n" +
                "Daily Visitors: " + getUsageFrequency() + "\n" +
                "Op. Cost    : Rs." + String.format("%.0f", calculateOperationalCost()) + "\n";
    }

    public int getTotalSeats()    { return totalSeats; }
    public int getOccupiedSeats() { return occupiedSeats; }
    public List<Book> getBooks()  { return new ArrayList<>(books); }

    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    @Override
    public String toString() {
        return "[" + getEntityID() + "] " + getName() + " | Books: " + books.size() +
               " (Available: " + getAvailableBooks().size() + ") | Seats: " + occupiedSeats + "/" + totalSeats;
    }
}
