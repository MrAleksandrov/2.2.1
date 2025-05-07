package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User selectUserByCarSeries(String model, int series) {
      try {
         TypedQuery<User> query = sessionFactory.getCurrentSession()
                 .createQuery("FROM User u WHERE u.car.model = :model AND u.car.series = :series", User.class)
                 .setParameter("model", model)
                 .setParameter("series", series);
         return query.getSingleResult();
      } catch (NoResultException e) {
         throw new RuntimeException("Пользователь с моделью машины " + model + " и серией " + series + " не найден");
      }
   }
}
