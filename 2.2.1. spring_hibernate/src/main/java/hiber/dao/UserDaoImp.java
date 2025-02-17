package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

   private TypedQuery<User> getUserQuery(String query) {
      return sessionFactory.getCurrentSession().createQuery(query);
   }

   @Override
   public User getUserByCar(String model, int series) {
      return getUserQuery("from User where " +
              "car.model=:carModel and car.series=:carSeries")
              .setParameter("carModel", model)
              .setParameter("carSeries", series).getSingleResult();
   }

   @Override
   public User getUserById(Long id) {
      return getUserQuery("from User where id=:id").setParameter("id", id).getSingleResult();
   }
}