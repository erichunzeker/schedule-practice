package practice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "doctor")
public class DoctorEntity {
    private Integer docId;
    private String docName;
    @JsonIgnore
    private Collection<PatientEntity> patientsByDocId;
    @JsonIgnore
    private Collection<ScheduleEntity> schedulesByDocId;

    @Id
    @Column(name = "doc_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    @Basic
    @Column(name = "doc_name")
    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoctorEntity that = (DoctorEntity) o;

        if (docId != that.docId) return false;
        if (docName != null ? !docName.equals(that.docName) : that.docName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = docId;
        result = 31 * result + (docName != null ? docName.hashCode() : 0);
        return result;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctorByDocId")
    public Collection<PatientEntity> getPatientsByDocId() {
        return patientsByDocId;
    }

    public void setPatientsByDocId(Collection<PatientEntity> patientsByDocId) {
        this.patientsByDocId = patientsByDocId;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctorByDocId")
    public Collection<ScheduleEntity> getSchedulesByDocId() {
        return schedulesByDocId;
    }

    public void setSchedulesByDocId(Collection<ScheduleEntity> schedulesByDocId) {
        this.schedulesByDocId = schedulesByDocId;
    }
}
