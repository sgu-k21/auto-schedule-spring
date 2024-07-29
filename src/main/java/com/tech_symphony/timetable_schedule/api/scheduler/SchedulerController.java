package com.tech_symphony.timetable_schedule.api.scheduler;

import com.tech_symphony.timetable_schedule.api.course.CourseRepository;
import com.tech_symphony.timetable_schedule.api.school_year.SchoolYearRepository;
import com.tech_symphony.timetable_schedule.model.Course;
import com.tech_symphony.timetable_schedule.model.CourseDay;
import com.tech_symphony.timetable_schedule.model.SchoolYear;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor
public class SchedulerController {

    private final CourseRepository courseRepository;
    private final SchoolYearRepository schoolYearRepository;

    private static class GetCourseListBody{
        public long yearId;
    }

    @GetMapping
    Set<Course> getCourseList(@RequestParam(required = true) long yearId) throws Exception {
        Optional<SchoolYear> year = schoolYearRepository.findById(yearId);
        if (year.isEmpty())
            throw new Exception("Year not found");


        Set<Course> courses = year.get().getCourses();
//        List<Course> courses = (List<Course>) courseRepository.findAll(PageRequest.of(0, 50));
        Set<Course> result = new HashSet<>();
        for (Course course : courses) {
            if (course.getPracticeGroup() == null)
                result.add(course);
//            else {
////                course.getCourseLink().getCourseDays().addAll(course.getCourseDays());
//            }
        }
        return result;
    }

}
