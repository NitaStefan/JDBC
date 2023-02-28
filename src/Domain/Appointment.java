package Domain;
import java.time.LocalDateTime;

public class Appointment implements Model<String>{
    String id;
    LocalDateTime date;
    Integer idPatient;
    public Appointment(String id,LocalDateTime date,Integer idPatient){
        this.id=id;
        this.date=date;
        this.idPatient=idPatient;
    }
    public Appointment(){
        this("0",LocalDateTime.of(2022, 4, 24, 14, 33),0);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public Integer getHour(){
        return getDate().getHour();
    }
    public Integer getYear(){
        return getDate().getYear();
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getIdPatient() {
        return idPatient;
    }

    public void setPatient(Integer idPatient) {
        this.idPatient = idPatient;
    }

    @Override
    public String toString() {
        return "Appointment(with id:"+id+") at "+date+" ,for patient with id: "+String.valueOf(idPatient);
    }
}
