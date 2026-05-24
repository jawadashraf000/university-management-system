package model.facility;

import java.io.Serializable;

public class Book implements Serializable {

    private String  bookID;
    private String  title;
    private String  author;
    private boolean isAvailable;
    private String  borrowedByStudentID;

    public Book(String bookID, String title, String author) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public boolean checkout(String studentID) {
        if (!isAvailable) {
            return false;
        }
        this.isAvailable = false;
        this.borrowedByStudentID = studentID;
        return true;
    }

    public void returnBook() {
        this.isAvailable = true;
        this.borrowedByStudentID = null;
    }

    public String getBookID()    { return bookID; }
    public String getTitle()     { return title; }
    public String getAuthor()    { return author; }
    public boolean isAvailable() { return isAvailable; }
    public String getBorrowedByStudentID() { return borrowedByStudentID; }

    public void setTitle(String title)  { this.title = title; }
    public void setAuthor(String author) { this.author = author; }

    @Override
    public String toString() {
        return "[" + bookID + "] " + title + " by " + author +
               " | " + (isAvailable ? "Available" : "Borrowed by " + borrowedByStudentID);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Book)) return false;
        return bookID.equals(((Book) obj).bookID);
    }
}
