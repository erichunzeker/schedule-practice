package practice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "front_desk_staff")
public class FrontDeskStaffEntity {
    private Integer frontDeskStaffId;
    private String firstName;
    private String lastName;
    @JsonIgnore
    private Collection<ScheduleEntity> schedulesByFrontDeskStaffId;

    @Id
    @Column(name = "front_desk_staff_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getFrontDeskStaffId() {
        return frontDeskStaffId;
    }

    public void setFrontDeskStaffId(Integer frontDeskStaffId) {
        this.frontDeskStaffId = frontDeskStaffId;
    }

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FrontDeskStaffEntity that = (FrontDeskStaffEntity) o;

        if (frontDeskStaffId != that.frontDeskStaffId) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;

        return true;
    }


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "frontDeskStaffByFrontDeskStaffId")
    public Collection<ScheduleEntity> getSchedulesByFrontDeskStaffId() {
        return schedulesByFrontDeskStaffId;
    }

    public void setSchedulesByFrontDeskStaffId(Collection<ScheduleEntity> schedulesByFrontDeskStaffId) {
        this.schedulesByFrontDeskStaffId = schedulesByFrontDeskStaffId;
    }
}
