package practice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "schedule")
public class ScheduleEntity {
    private Integer scheduleId;

    private FrontDeskStaffEntity frontDeskStaffByFrontDeskStaffId;

    private DoctorEntity doctorByDocId;
    @JsonIgnore
    private Collection<VisitEntity> visitsByScheduleId;

    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleEntity that = (ScheduleEntity) o;

        if (scheduleId != that.scheduleId) return false;

        return true;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "front_desk_staff_id", referencedColumnName = "front_desk_staff_id", nullable = false)
    public FrontDeskStaffEntity getFrontDeskStaffByFrontDeskStaffId() {
        return frontDeskStaffByFrontDeskStaffId;
    }

    public void setFrontDeskStaffByFrontDeskStaffId(FrontDeskStaffEntity frontDeskStaffByFrontDeskStaffId) {
        this.frontDeskStaffByFrontDeskStaffId = frontDeskStaffByFrontDeskStaffId;
    }

    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "doc_id", referencedColumnName = "doc_id", nullable = false)
    public DoctorEntity getDoctorByDocId() {
        return doctorByDocId;
    }

    public void setDoctorByDocId(DoctorEntity doctorByDocId) {
        this.doctorByDocId = doctorByDocId;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scheduleByScheduleId")
    public Collection<VisitEntity> getVisitsByScheduleId() {
        return visitsByScheduleId;
    }

    public void setVisitsByScheduleId(Collection<VisitEntity> visitsByScheduleId) {
        this.visitsByScheduleId = visitsByScheduleId;
    }
}
