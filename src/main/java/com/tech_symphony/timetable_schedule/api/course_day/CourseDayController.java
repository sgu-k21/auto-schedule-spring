package com.tech_symphony.timetable_schedule.api.course_day;


import com.tech_symphony.timetable_schedule.api.course.CourseRepository;
import com.tech_symphony.timetable_schedule.model.CourseDay;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course_day")
public class CourseDayController {


    private final CourseDayRepository courseDayRepository;

    @GetMapping()
    public CourseDay getCourseDay(){
        return courseDayRepository.findById((long) 1044).get();
    }

}
