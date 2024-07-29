package com.tech_symphony.timetable_schedule.api.Import;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_symphony.timetable_schedule.api.course.CourseRepository;
import com.tech_symphony.timetable_schedule.api.course_day.CourseDayRepository;
import com.tech_symphony.timetable_schedule.api.school_year.SchoolYearRepository;
import com.tech_symphony.timetable_schedule.model.Course;
import com.tech_symphony.timetable_schedule.model.CourseDay;
import com.tech_symphony.timetable_schedule.model.SchoolYear;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequiredArgsConstructor()
@RequestMapping("/import")
public class ImportController {

    private final FileService fileService;
    private final CourseRepository courseRepository;
    private final CourseDayRepository courseDayRepository;
    private final SchoolYearRepository schoolYearRepository;
//    private final SchoolYearRepository
    static class ImportBody {
        @NotNull
        public Long yearId;

    }

    @PostMapping()
//    @Transactional()
    int importFromPDF(@Valid @RequestBody ImportBody importBody) throws IOException {

        Optional<SchoolYear> year = schoolYearRepository.findById(importBody.yearId);
        if (year.isEmpty()) throw new IllegalArgumentException("Invalid year");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String fileValue = fileService.readFileFromResources("/python/schedule_cntt_3_2023_2024.json");
        JsonNode json =  mapper.readTree(fileValue);
        List<JsonNode> scheduler = mapper.readValue(json.get("scheduler").toString(), new TypeReference<List<JsonNode>>(){});
        int count = 0;
        for (JsonNode schedulerNode : scheduler) {
            Course course = mapper.readValue(schedulerNode.toString(), Course.class);
            course.setYear(year.get());
            try {
                Course courseFind = courseRepository.findCourseByCourseIdAndCourseGroupAndPracticeGroupAndYear(course.getCourseId(), course.getCourseGroup(), course.getPracticeGroup(), year.get());
                if (courseFind == null)
                    courseRepository.save(course);
                else
                    course = courseFind;

                courseDayRepository.deleteAll(course.getCourseDays());

                List<CourseDay> courseDays = mapper.readValue(schedulerNode.get("days").toString(), new TypeReference<List<CourseDay>>(){} );
//                return courseDays;
                Course defaultCourse = courseRepository.findById(course.getId()).get();
                for (CourseDay courseDay : courseDays) {
                    System.out.println(!Objects.equals(courseDay.getPracticeGroup(), course.getPracticeGroup()));
                    if (!Objects.equals(courseDay.getPracticeGroup(), course.getPracticeGroup())){
                        Course find = courseRepository.findCourseByCourseIdAndCourseGroupAndPracticeGroupAndYear(
                                course.getCourseId(),
                                course.getCourseGroup(),
                                courseDay.getPracticeGroup(),
                                year.get()
                        );
                        if (find == null){
                            course = course.clone();
                            course.setCourses(null);
                            course.setPracticeGroup(courseDay.getPracticeGroup());
                            course.setCourseDays(null);
                            if (course.getPracticeGroup() != null)
                                course.setCourseLink(defaultCourse);
                            courseRepository.save(course);
                        }
                        else
                            course = find;
                    }

                   courseDay.setCourse(course);
                   courseDayRepository.save(courseDay);
                   count++;
                }
//                throw new IOException("test");new
            }catch (Exception e) {
                System.out.println(e.getMessage());
                return count;
            }
        }
        return count;

//        return courseDayRepository.findAll();
    }
}
