package com.tech_symphony.timetable_schedule.api.course;

import com.tech_symphony.timetable_schedule.model.Course;
import com.tech_symphony.timetable_schedule.model.SchoolYear;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {

    public Course findCourseByCourseIdAndCourseGroupAndPracticeGroupAndYear(@NotNull String courseId, @NotNull Short courseGroup, Short practiceGroup, @NotNull SchoolYear year);

    List<Course> findAll(Pageable pageable);
}

