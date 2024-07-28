package com.tech_symphony.timetable_schedule.api.course_day;

import com.tech_symphony.timetable_schedule.model.Course;
import com.tech_symphony.timetable_schedule.model.CourseDay;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseDayRepository extends JpaRepository<CourseDay, Long> {
}

