package edu.prathap.reminder.repo;

import edu.prathap.reminder.entity.CustomUser;
import edu.prathap.reminder.entity.Reminder;
import edu.prathap.reminder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<CustomUser,Long> {
    CustomUser findByUsername(String username);
    public CustomUser findAllByUsernameAndPassword(@Param("username") String username,@Param("password") String password);
}
