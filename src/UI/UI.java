package UI;

import Domain.Appointment;
import Domain.Patient;
import Service.ServicePatients;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class UI {
    private ServicePatients service;

    public UI(ServicePatients service) {
        this.service = service;
    }
    public void menu(){
        System.out.println("0 - exit");
        System.out.println("1 - add patient");
        System.out.println("2 - delete patient");
        System.out.println("3 - update patient");
        System.out.println("4 - find patient");
        System.out.println("5 - add appointment");
        System.out.println("6 - delete appointment");
        System.out.println("7 - sort patients by age");
        System.out.println("8 - show reports");
        System.out.println("9 - show all patients and appointments");
    }
    public void reports(){
        System.out.println("1 - appointments of a patient");
        System.out.println("2 - appointments scheduled afternoon");
        System.out.println("3 - all adult patients");
        System.out.println("4 - adults displayed alphabetically");
        System.out.println("5 - group appointments by year");
    }
    public void addP()
    {
        Scanner sc=new Scanner(System.in);
        System.out.print("ID=");
        Integer id=sc.nextInt();
        System.out.print("Name=");
        String name=sc.next();
        System.out.print("Age=");
        Integer age=sc.nextInt();
        System.out.print("Illness=");
        String illness=sc.next();
        Patient patient=new Patient(id,name,age,illness);
        try{
        service.getPtRepo().add(id,patient);
        showAllPatients();
        } catch (RuntimeException re){
            System.out.println(re.getMessage());
        }
    }
    public void deleteP()
    {
        Scanner sc=new Scanner(System.in);
        System.out.print("ID=");
        Integer id=sc.nextInt();
        try{
            service.removePatient(id);
            showAll();
        } catch (RuntimeException re) {
            System.out.println(re.getMessage());
        }
    }
    public void updateP(){
        Scanner sc=new Scanner(System.in);
        System.out.print("ID=");
        Integer id=sc.nextInt();
        System.out.print("Name=");
        String name=sc.next();
        System.out.print("Age=");
        Integer age=sc.nextInt();
        System.out.print("Illness=");
        String illness=sc.next();
        Patient patient=new Patient(id,name,age,illness);
        try {
            service.getPtRepo().updateId(id, patient);
            showAllPatients();
        } catch (RuntimeException re) {
            System.out.println(re.getMessage());
        }
    }
    public void findP(){
        Scanner sc=new Scanner(System.in);
        System.out.print("ID=");
        Integer id=sc.nextInt();
        try {
            System.out.println(service.getPtRepo().findById(id));
        }catch (RuntimeException re) {
            System.out.println(re.getMessage());
        }
    }
    public void addA()
    {
        Scanner sc=new Scanner(System.in);
        System.out.print("ID=");
        String id=sc.next();
        System.out.print("year=");
        int year=sc.nextInt();
        System.out.print("month=");
        int month=sc.nextInt();
        System.out.print("day=");
        int day=sc.nextInt();
        System.out.print("hour=");
        int hour=sc.nextInt();
        System.out.print("minutes=");
        int minutes=sc.nextInt();
        System.out.print("For patient with id=");
        Integer idP= sc.nextInt();
        Appointment app=new Appointment(id, LocalDateTime.of(year,month,day,hour,minutes),idP);
        try {
            service.getAppRepo().add(id, app);
            showAllAppointments();
        }catch (RuntimeException re) {
            System.out.println(re.getMessage());
        }
    }
    public void deleteA(){
        Scanner sc=new Scanner(System.in);
        System.out.print("ID=");
        String id=sc.next();
        try {
            service.getAppRepo().delete(id);
            showAllAppointments();
        }catch (RuntimeException re) {
            System.out.println(re.getMessage());
        }
    }
    public void sort(){
        ArrayList<Patient> patients=service.sortByAge();
        patients.forEach(System.out::println);
    }
    public void showAllPatients(){
        Iterable<Patient> iterable=service.getPtRepo().findAll();
        Iterator<Patient> it= iterable.iterator();
        while(it.hasNext())
            System.out.println(it.next());
    }
    public void showAllAppointments(){
        Iterable<Appointment> iterable=service.getAppRepo().findAll();
        Iterator<Appointment> it= iterable.iterator();
        while(it.hasNext())
            System.out.println(it.next());
    }
    public void showAll(){
        showAllPatients();
        showAllAppointments();
    }
    public void displayPatientsUsingConsumer()//Consumer
    {
        Consumer<Patient> print = a -> System.out.println(a);
        Iterable<Patient> itrb=service.getPtRepo().findAll();
        Iterator<Patient> it=itrb.iterator();
        while (it.hasNext())
            print.accept(it.next());

    }
    public void adultPatients()//Predicate
    {
        Iterable<Patient> itrb=service.getPtRepo().findAll();
        Iterator<Patient> it=itrb.iterator();
        Predicate<Patient> predicate= i->(i.getAge()>=18);
        while (it.hasNext())
        {
            System.out.println(predicate.test(it.next()));
        }
    }
    public void filterByAge()//Stream filter
    {
        Iterable<Patient> itrb=service.getPtRepo().findAll();
        Iterator<Patient> it=itrb.iterator();
        ArrayList<Patient> patients=new ArrayList<>();
        while(it.hasNext()) {
            patients.add(it.next());
        }
        patients.stream().filter(p->p.getAge()%2==0).forEach(System.out::println);
    }
    public void sortPatients()//Stream sort
    {
        Iterable<Patient> itrb=service.getPtRepo().findAll();
        Iterator<Patient> it=itrb.iterator();
        ArrayList<Patient> patients=new ArrayList<>();
        while(it.hasNext()) {
            patients.add(it.next());
        }
        patients.stream().sorted((p1,p2)->(p1.getId().compareTo(p2.getId()))).forEach(System.out::println);
    }
    public void report1()
    {
        Scanner sc=new Scanner(System.in);
        System.out.print("Patient's id=");
        Integer id=sc.nextInt();
        ArrayList<Appointment> apps=service.patientsAppointments(id);
        apps.forEach(System.out::println);
    }
    public void report2()
    {
        ArrayList<Appointment> apps=service.afternoonAppointments();
        apps.forEach(System.out::println);
    }
    public void report3()
    {
        ArrayList<Patient> patients=service.adultPatients();
        patients.forEach(System.out::println);
    }
    public void report4()
    {
        ArrayList<Patient> patients=service.patientsAlphabetically();
        patients.forEach(System.out::println);
    }
    public void report5()
    {
        HashMap<Integer, List<Appointment>> apps=service.groupAppsByYear();
        apps.forEach((year,apps2) -> {
            System.out.println(year);
            apps2.forEach(System.out::println);
        });
    }


    public void main(){
        menu();
        Scanner sc=new Scanner(System.in);
        System.out.print("option=");
        int option=sc.nextInt();
        while(option!=0){
            if(option==1)
                addP();
            else if (option==2)
                deleteP();
            else if(option==3)
                updateP();
            else if(option==4)
                findP();
            else if(option==5)
                addA();
            else if(option==6)
                deleteA();
            else if(option==7)
                sort();
            else if(option==8)
            {
                reports();
                System.out.println("Choose report");
                Integer opt= sc.nextInt();
                if(opt==1)
                    report1();
                else if(opt==2)
                    report2();
                else if(opt==3)
                    report3();
                else if(opt==4)
                    report4();
                else report5();
            }
            else showAll();
            System.out.print("option=");
            option=sc.nextInt();
        }
    }
}
