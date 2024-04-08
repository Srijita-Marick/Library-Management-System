package sea.cpsc233projectgui.objects;

import sea.cpsc233projectgui.util.BookType;

public class PhysicalBooks extends Books {
    public PhysicalBooks(String title, String author, String genre, String availabilityStatus) {
        super(title, author, genre, availabilityStatus, BookType.PHYSICAL);
    }

    @Override
    public String toString() {
        StringBuilder bookString = new StringBuilder();
        bookString.append("\n--------------------");
        bookString.append("\nTitle: ").append(this.getTitle());
        bookString.append("\nAuthor: ").append(this.getAuthor());
        bookString.append("\nGenre: ").append(this.getGenre());
        bookString.append("\nType: PHYSICAL");
        bookString.append("\nStatus: ").append(this.getAvailabilityStatus());
        return bookString.toString();
    }
}
