package com.tech_symphony.timetable_schedule.api.school_year;

import com.tech_symphony.timetable_schedule.model.SchoolYear;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolYearService {

    @Autowired
    private SchoolYearRepository schoolYearRepository;

    public List<SchoolYear> getAll() {
        return schoolYearRepository.findAll();
    }

//    public SchoolYear create(SchoolYear schoolYear) {
//
//    }

}
