package org.launchcode.mealplanner.models.data;

import org.launchcode.mealplanner.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
