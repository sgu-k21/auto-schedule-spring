package com.tech_symphony.timetable_schedule.api.scheduler;

import com.tech_symphony.timetable_schedule.api.course.CourseRepository;
import com.tech_symphony.timetable_schedule.model.Course;
import com.tech_symphony.timetable_schedule.model.CourseDay;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor
public class SchedulerController {

    private final CourseRepository courseRepository;

    @GetMapping
    List<Course> getCourseList(){
        List<Course> courses = (List<Course>) courseRepository.findAll(PageRequest.of(0, 50));
        List<Course> result = new ArrayList<>();
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
