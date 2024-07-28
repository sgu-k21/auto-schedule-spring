package com.tech_symphony.timetable_schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "course", uniqueConstraints = {
        @UniqueConstraint(name = "course_course_id_course_group_practice_group_year_id_key", columnNames = {"course_id", "course_group", "practice_group", "year_id"})
})
@JsonIgnoreProperties({"year", "courseLink"})
public class Course implements Cloneable{

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "course_link")
    private Course courseLink;

    @OneToMany(mappedBy = "courseLink")
    private Set<Course> courses = new LinkedHashSet<>();

    @Override
    public Course clone() {
        try {
            Course clone = (Course) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            clone.setId(null);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Getter
    @Setter
    public static class Index {
        private String courseId;
        private long courseGroup;
        private long yearId;

        public static Index fromCourse(Course course){
            return new Index(course.getCourseId(), course.getCourseGroup(), course.year.getId());
        }

        public Index(String courseId, long courseGroup, long yearId) {
            this.courseId = courseId;
            this.courseGroup = courseGroup;
            this.yearId = yearId;
        }



    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "course_id", nullable = false, length = Integer.MAX_VALUE)
    private String courseId;

    @Column(name = "course_name", length = Integer.MAX_VALUE)
    private String courseName;

    @NotNull
    @Column(name = "course_group", nullable = false)
    private Short courseGroup;

    @Column(name = "practice_group")
    private Short practiceGroup;

    @Column(name = "amount")
    private Short amount;

    @Column(name = "teacher_name", length = Integer.MAX_VALUE)
    private String teacherName;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "year_id", nullable = false)
    private SchoolYear year;

    @OneToMany(mappedBy = "course")
    private Set<CourseDay> courseDays = new LinkedHashSet<>();

}