package practice.model;

import javax.persistence.*;

@Entity
@Table(name = "visit")
public class VisitEntity {
    private Integer visitId;
    private Integer patId;
    //@JsonIgnore
    private ScheduleEntity scheduleByScheduleId;

    @Id
    @Column(name = "visit_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getVisitId() {
        return visitId;
    }

    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }

    @Basic
    @Column(name = "pat_id")
    public Integer getPatId() {
        return patId;
    }

    public void setPatId(Integer patId) {
        this.patId = patId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VisitEntity that = (VisitEntity) o;

        if (visitId != that.visitId) return false;
        if (patId != that.patId) return false;

        return true;
    }


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id")
    public ScheduleEntity getScheduleByScheduleId() {
        return scheduleByScheduleId;
    }

    public void setScheduleByScheduleId(ScheduleEntity scheduleByScheduleId) {
        this.scheduleByScheduleId = scheduleByScheduleId;
    }
}
