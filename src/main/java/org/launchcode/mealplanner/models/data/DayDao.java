package org.launchcode.mealplanner.models.data;

import org.launchcode.mealplanner.models.Day;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface DayDao extends CrudRepository<Day, Integer> {
    Day findByName(String name);
}
