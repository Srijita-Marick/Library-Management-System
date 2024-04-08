package sea.cpsc233projectgui.objects;

import sea.cpsc233projectgui.Data;
import sea.cpsc233projectgui.util.MemberType;

public class AdultMember extends Member {
    private double fines;
    public AdultMember(int id, String name) {
        super(id, name, MemberType.ADULT);
        this.fines=0.00;
    }
    public double getFines() {
        return fines;
    }
    public void setFines(double fines) {
        this.fines = fines;
    }

    public String toString() {
        StringBuilder memString = new StringBuilder();
        memString.append("\n--------------------");
        memString.append("\nID: ").append(this.getID());
        memString.append("\nName: ").append(this.getName());
        memString.append("\nMember Type: Adult");
        memString.append("\nBooks Borrowed:");
        for (String book: Data.getBorrowedBooks(this.getID())){
            memString.append("\n     ").append(book);
        }
        String roundedFine = String.format("%.2f",(this.getFines()));
        memString.append("\nFines: ").append(roundedFine);
        return memString.toString();
    }
}