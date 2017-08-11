package practice.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "patient")
public class PatientEntity {
    private Integer patId;
    private String name;
    private Collection<ContactEntity> contactsByPatId;
    private DoctorEntity doctorByDocId;

    @Id
    @Column(name = "pat_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getPatId() {
        return patId;
    }

    public void setPatId(Integer patId) {
        this.patId = patId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatientEntity that = (PatientEntity) o;

        if (patId != that.patId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientByPatId")
    public Collection<ContactEntity> getContactsByPatId() {
        return contactsByPatId;
    }

    public void setContactsByPatId(Collection<ContactEntity> contactsByPatId) {
        this.contactsByPatId = contactsByPatId;
    }

    @ManyToOne
    @JoinColumn(name = "doc_id", referencedColumnName = "doc_id")
    public DoctorEntity getDoctorByDocId() {
        return doctorByDocId;
    }

    public void setDoctorByDocId(DoctorEntity doctorByDocId) {
        this.doctorByDocId = doctorByDocId;
    }

}

