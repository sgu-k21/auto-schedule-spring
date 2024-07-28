package com.tech_symphony.timetable_schedule.api.school_year;

import com.tech_symphony.timetable_schedule.model.SchoolYear;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor()
@RequestMapping("/school-year")
public class SchoolYearController {

    private final SchoolYearService schoolYearService;

    private final SchoolYearRepository schoolYearRepository;

    @GetMapping()
    List<SchoolYear> all(){
        return schoolYearRepository.findAll();
    }

    @PostMapping()
    SchoolYear create(@RequestBody SchoolYear schoolYear) {
//        System.out.println(schoolYear);
        return schoolYearRepository.saveAndFlush(schoolYear);
//        return schoolYearRepository.save(schoolYear);
    }

}
