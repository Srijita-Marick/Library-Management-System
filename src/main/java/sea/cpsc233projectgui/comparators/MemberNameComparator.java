package sea.cpsc233projectgui.comparators;

import sea.cpsc233projectgui.objects.Member;

import java.util.Comparator;

public class MemberNameComparator implements Comparator<Member> {

    @Override
    public int compare(Member o1, Member o2) {
        int comp = o1.getName().compareTo(o2.getName());
        if(comp!=0){
            return comp;
        }
        return o1.compareTo(o2);
    }
}
