package com.tech_symphony.timetable_schedule.api.school_year;

import com.tech_symphony.timetable_schedule.model.SchoolYear;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolYearRepository extends JpaRepository<SchoolYear, Long> {
    public Optional<SchoolYear> findByFromAndToAndPeriod(@NotNull Short from, @NotNull Short to, @NotNull Short period);
}
