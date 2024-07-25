package com.tech_symphony.timetable_schedule.api.school_year;

import com.tech_symphony.timetable_schedule.model.SchoolYear;

import java.util.List;

public class SchoolYearService {
    private SchoolYearRepository schoolYearRepository;

    public List<SchoolYear> getAll() {
        return schoolYearRepository.findAll();
    }

}
