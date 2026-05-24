package controller;

import model.facility.*;
import persistence.AppState;
import java.util.List;

public class FacilityController {

    private AppState state;

    public FacilityController(AppState state) {
        this.state = state;
    }

    public boolean addLibrary(Library lib) {
        if (state.getLibraryRepository().findById(lib.getEntityID()) != null)
            return false;

        state.getLibraryRepository().add(lib);
        return true;
    }

    public boolean removeLibrary(String id) {
        return state.getLibraryRepository().remove(id);
    }

    public Library findLibrary(String id) {
        return state.getLibraryRepository().findById(id);
    }

    public List<Library> getAllLibraries() {
        return state.getLibraryRepository().getAll();
    }

    public boolean addBook(String libraryID, Book book) {
        Library lib = findLibrary(libraryID);
        if (lib == null) return false;

        lib.addBook(book);

        if (!state.getAllBooks().contains(book))
            state.getAllBooks().add(book);

        return true;
    }

    public boolean checkoutBook(String libraryID, String bookID, String studentID) {
        Library lib = findLibrary(libraryID);
        return lib != null && lib.checkoutBook(bookID, studentID);
    }

    public boolean returnBook(String libraryID, String bookID) {
        Library lib = findLibrary(libraryID);
        return lib != null && lib.returnBook(bookID);
    }

    public boolean addCafeteria(Cafeteria caf) {
        if (state.getCafeteriaRepository().findById(caf.getEntityID()) != null)
            return false;

        state.getCafeteriaRepository().add(caf);
        return true;
    }

    public boolean removeCafeteria(String id) {
        return state.getCafeteriaRepository().remove(id);
    }

    public List<Cafeteria> getAllCafeterias() {
        return state.getCafeteriaRepository().getAll();
    }

    public boolean addHostel(Hostel hostel) {
        if (state.getHostelRepository().findById(hostel.getEntityID()) != null)
            return false;

        state.getHostelRepository().add(hostel);
        return true;
    }

    public boolean removeHostel(String id) {
        return state.getHostelRepository().remove(id);
    }

    public Hostel findHostel(String id) {
        return state.getHostelRepository().findById(id);
    }

    public List<Hostel> getAllHostels() {
        return state.getHostelRepository().getAll();
    }
}