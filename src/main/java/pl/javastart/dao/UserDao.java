package pl.javastart.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.javastart.model.User;
import pl.javastart.model.UserDetails;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserDao {
    private EntityManager entityManager;

    @Autowired
    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(User user) {
        UserDetails details = user.getDetails();
        if (details != null && details.getId() == null) {
            entityManager.persist(details);
        }
        entityManager.persist(user);
    }

    public User get(Long id) {
        return entityManager.find(User.class, id);
    }

    public void update(User user) {
        UserDetails details = user.getDetails();
        if (details != null) {
            details = entityManager.merge(details);
            user.setDetails(details);
        }
        entityManager.merge(user);
    }

    public void delete(User user) {
        User userToDelete = entityManager.merge(user);
        entityManager.remove(userToDelete.getDetails());
        entityManager.remove(userToDelete);

    }
}
