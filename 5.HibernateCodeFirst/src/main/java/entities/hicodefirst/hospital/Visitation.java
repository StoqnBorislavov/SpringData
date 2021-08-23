package entities.hicodefirst.hospital;

import entities.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity {
    private LocalDateTime date;
    private String comments;
    private Patient patient;
    private Diagnose diagnose;
    private Set<Medicament> medicamentSet;

    public Visitation() {
    }

    @Column(name = "date", nullable = false)
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    @Column(name = "comments", nullable = false)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @ManyToMany
    @JoinTable(name = "visitations_medicaments",
            joinColumns = @JoinColumn(name="visitation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="medicament_id", referencedColumnName = "id"))
    public Set<Medicament> getMedicamentSet() {
        return medicamentSet;
    }
    @ManyToOne
    @JoinColumn(name="patient_id", referencedColumnName = "id")
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setMedicamentSet(Set<Medicament> medicamentSet) {
        this.medicamentSet = medicamentSet;
    }

    @ManyToOne
    @JoinColumn(name = "diagnose_id", referencedColumnName = "id")
    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }
}
