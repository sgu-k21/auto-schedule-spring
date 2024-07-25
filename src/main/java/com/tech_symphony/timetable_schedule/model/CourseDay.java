package com.tech_symphony.timetable_schedule.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "\"CourseDay\"", uniqueConstraints = {
        @UniqueConstraint(name = "days_id_key", columnNames = {"id"})
})
public class CourseDay {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CourseDay_id_gen")
    @SequenceGenerator(name = "CourseDay_id_gen", sequenceName = "days_id_seq1", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "day_of_week", nullable = false)
    private Short dayOfWeek;

    @NotNull
    @Column(name = "period_start", nullable = false)
    private Short periodStart;

    @NotNull
    @Column(name = "period_total", nullable = false)
    private Short periodTotal;

    @Column(name = "practice_group")
    private Short practiceGroup;

    @Column(name = "room", length = Integer.MAX_VALUE)
    private String room;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

}