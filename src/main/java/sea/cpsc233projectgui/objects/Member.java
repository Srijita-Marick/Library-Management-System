package sea.cpsc233projectgui.objects;

import sea.cpsc233projectgui.util.MemberType;

import java.util.ArrayList;

public abstract class Member {
    private final String name;
    private final int id;
    private final ArrayList<String> borrowed;
    private final MemberType memberType;

    public Member(int id,String name,MemberType memberType){
        this.id = id;
        this.name = name;
        this.borrowed = new ArrayList<String>(); //when someone joins library for first time they don't have books yet
        this.memberType=memberType;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MemberType getMemberType() {
        return memberType;
    }


    public ArrayList<String> getBorrowed() {
        return borrowed;
    }

    @Override
    public abstract String toString() ;

    public void addBookToMember(String book){
        borrowed.add(book);
        if (this instanceof ChildMember childMember){
            childMember.addToReadCount();
        }
    }
    public void removeBookFromMember(String book){
        borrowed.remove(book);
    }

    public int compareTo(Member member){
        return Integer.compare(id,member.id);
    }
}
