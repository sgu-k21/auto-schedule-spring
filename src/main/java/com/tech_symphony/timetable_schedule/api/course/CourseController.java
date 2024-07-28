package com.tech_symphony.timetable_schedule.api.course;


import com.tech_symphony.timetable_schedule.model.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseRepository courseRepository;

    @GetMapping
    List<Course> getCourse(){
        return courseRepository.findAll(PageRequest.of(0,10));
    }
}
