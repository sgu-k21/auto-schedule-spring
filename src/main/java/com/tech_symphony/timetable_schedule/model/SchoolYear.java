package com.tech_symphony.timetable_schedule.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "\"SchoolYear\"", indexes = {
        @Index(name = "SchoolYear_from_to_start_at_period_key", columnList = "from, to, start_at, period", unique = true)
})
public class SchoolYear {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SchoolYear_id_gen")
    @SequenceGenerator(name = "SchoolYear_id_gen", sequenceName = "days_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = Integer.MAX_VALUE)
    private String title;

    @NotNull
    @Column(name = "\"from\"", nullable = false)
    private Short from;

    @NotNull
    @Column(name = "\"to\"", nullable = false)
    private Short to;

    @Column(name = "start_at")
    private LocalDate startAt;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @NotNull
    @Column(name = "period", nullable = false)
    private Short period;

    @OneToMany(mappedBy = "year")
    private Set<Course> courses = new LinkedHashSet<>();

}