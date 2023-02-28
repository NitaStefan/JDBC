package Domain;

import java.util.Comparator;

public class CompareByAge implements Comparator<Patient> {
    public int compare(Patient p1,Patient p2){
        if(p1.getAge()>p2.getAge())
            return 1;
        else if(p1.getAge()==p2.getAge())
            return 0;
        else return -1;
    }
}
