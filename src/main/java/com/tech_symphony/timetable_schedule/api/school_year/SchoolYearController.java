package com.tech_symphony.timetable_schedule.api.school_year;

import com.tech_symphony.timetable_schedule.model.SchoolYear;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SchoolYearController {

    private SchoolYearService schoolYearService;

    @GetMapping("/school-year")
    List<SchoolYear> all(){
        return schoolYearService.getAll();
    }

}
