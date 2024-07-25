package com.tech_symphony.timetable_schedule.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "\"Course\"", indexes = {
        @Index(name = "Course_year_id_course_id_course_group_key", columnList = "year_id, course_id, course_group", unique = true)
})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Course_id_gen")
    @SequenceGenerator(name = "Course_id_gen", sequenceName = "courses_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "course_id", nullable = false, length = Integer.MAX_VALUE)
    private String courseId;

    @NotNull
    @Column(name = "course_name", nullable = false, length = Integer.MAX_VALUE)
    private String courseName;

    @NotNull
    @Column(name = "course_group", nullable = false)
    private Short courseGroup;

    @Column(name = "amount")
    private Short amount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "year_id", nullable = false)
    private SchoolYear year;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "course")
    private Set<CourseDay> courseDays = new LinkedHashSet<>();

}