package practice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "contact")
public class ContactEntity {
    private Integer contactId;
    private String contactName;
    @JsonIgnore
    private PatientEntity patientByPatId;

    @Id
    @Column(name = "contact_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    @Basic
    @Column(name = "contact_name")
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactEntity that = (ContactEntity) o;

        return Objects.equals(contactId, that.contactId) && (contactName != null ? contactName.equals(that.contactName) : that.contactName == null);
    }


    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "pat_id", referencedColumnName = "pat_id")
    public PatientEntity getPatientByPatId() {
        return patientByPatId;
    }

    public void setPatientByPatId(PatientEntity patientByPatId) {
        this.patientByPatId = patientByPatId;
    }
}
