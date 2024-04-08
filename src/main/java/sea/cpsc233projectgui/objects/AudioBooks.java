package sea.cpsc233projectgui.objects;

import sea.cpsc233projectgui.util.BookType;

public class AudioBooks extends Books {
    private final String narrator;
    public AudioBooks(String title, String author, String narrator, String genre, String availabilityStatus) {
        super(title, author, genre, availabilityStatus, BookType.AUDIO);
        this.narrator = narrator;
    }

    public String getNarrator(){
        return narrator;
    }
    @Override
    public String toString() {
        StringBuilder bookString = new StringBuilder();
        bookString.append("\n--------------------");
        bookString.append("\nTitle: ").append(this.getTitle());
        bookString.append("\nAuthor: ").append(this.getAuthor());
        bookString.append("\nNarrator: ").append(this.getNarrator());
        bookString.append("\nGenre: ").append(this.getGenre());
        bookString.append("\nType: AUDIO");
        bookString.append("\nStatus: ").append(this.getAvailabilityStatus());
        return bookString.toString();
    }
}
