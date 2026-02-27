package edu.prathap.reminder.repo;

import edu.prathap.reminder.entity.RmsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<RmsUser,Long> {
    RmsUser findByUsername(String username);
    public RmsUser findAllByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
