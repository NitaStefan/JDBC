import Domain.Appointment;
import Domain.Patient;
import Repository.AppointmentRepo;
import Repository.AppointmentRepoDB;
import Repository.PatientRepo;
import Repository.PatientRepositoryDB;
import Service.ServicePatients;
import UI.UI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class MainLab2 {
    public static void main(String[] args){
        PatientRepositoryDB pRepoDB=new PatientRepositoryDB("src/Repository/Hospital.db");
        AppointmentRepoDB appRepoDB=new AppointmentRepoDB("src/Repository/Hospital.db");

        ServicePatients service=new ServicePatients(pRepoDB,appRepoDB);

        UI ui=new UI(service);
        ui.main();

    }
}