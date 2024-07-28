package com.tech_symphony.timetable_schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "school_year")
@JsonIgnoreProperties({ "course" })
public class SchoolYear {
    @Id
    @Column(name = "id", nullable = false)
    private Short id;

    @Column(name = "title", length = Integer.MAX_VALUE)
    private String title;

    @NotNull
    @Column(name = "\"from\"", nullable = false)
    private Short from;

    @NotNull
    @Column(name = "\"to\"", nullable = false)
    private Short to;

    @NotNull
    @Column(name = "period", nullable = false)
    private Short period;

    @Column(name = "start_at")
    private LocalDate startAt;

    @Column(name = "created_at")
    @CreationTimestamp
    private OffsetTime createdAt;

    @OneToMany(mappedBy = "year")
    private Set<Course> courses = new LinkedHashSet<>();

}