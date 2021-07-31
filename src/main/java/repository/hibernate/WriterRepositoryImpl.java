package repository.hibernate;

import bootstrap.SessionRunner;
import model.Region;
import model.Writer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.common.WriterRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WriterRepositoryImpl implements WriterRepository {

    private final SessionFactory sessionFactory;
    private final PostRepositoryImpl postRepository;

    public WriterRepositoryImpl() {

        sessionFactory = SessionRunner.initSessionFactory();
        postRepository = new PostRepositoryImpl();
    }
    @Override
    public Writer save(Writer writer) {
        Transaction transaction = null;
        Writer res = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(writer.getRegion());
            System.out.println(writer.getPosts().size());
            res = session.get(Writer.class, session.save(writer));
            transaction.commit();
        } catch (IllegalStateException e) {
            Optional.ofNullable(transaction).ifPresent(t -> t.rollback());
        }
        return res;
    }

    @Override
    public Writer update(Writer writer) {
        if (writer != null) {
            EntityManager manager = sessionFactory.createEntityManager();
            manager.getTransaction().begin();
            int res = manager.createQuery("update Writer p set p.lastName=:lastName, p.region=:region, p.posts=:posts where id=:id")
                    .setParameter("lastName", writer.getLastName())
                    .setParameter("region", writer.getRegion())
                    .setParameter("posts", writer.getPosts())
                    .setParameter("id", writer.getId()).executeUpdate();
            manager.getTransaction().commit();
            return res >= 1 ? writer : null;
        }
        return null;
    }

    @Override
    public boolean delete(Integer integer) {
        Transaction transaction = null;
        boolean res = false;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            res = session.createQuery("delete from Writer w where w.id =:id").executeUpdate() >= 1;
            transaction.commit();
        } catch (Exception e) {
            Optional.ofNullable(transaction).ifPresent(EntityTransaction::rollback);
        }
        return res;
    }

    @Override
    public Writer findById(Integer integer) {
        Transaction transaction = null;
        Writer res = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            res = session.find(Writer.class, integer);
            transaction.commit();
        } catch (Exception e) {
            Optional.ofNullable(transaction).ifPresent(EntityTransaction::rollback);
        }
        return res;
    }

    @Override
    public List<Writer> getAll() {
        Session session = sessionFactory.openSession();
        List<Writer> writers = session.createQuery("from Writer", Writer.class).getResultList();
        session.beginTransaction().commit();
        return writers;
    }

    @Override
    public List<Writer> findByRegion(Region region) {
        Transaction transaction = null;
        List<Writer> res = Collections.emptyList();
        try(Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            res = session.createQuery("from Writer w where w.id =:id", Writer.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            Optional.ofNullable(transaction).ifPresent(EntityTransaction::rollback);
        }
        return res;
    }
}
