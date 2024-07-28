package com.tech_symphony.timetable_schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetTime;

@Getter
@Setter
@Entity
@Table(name = "course_day")
@JsonIgnoreProperties("course")
public class CourseDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "total_week", length = Integer.MAX_VALUE)
    private String totalWeek;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetTime createdAt;

}