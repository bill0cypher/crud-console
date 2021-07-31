package repository.hibernate;

import bootstrap.SessionRunner;
import model.Post;
import model.Writer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.common.PostRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class PostRepositoryImpl implements PostRepository {

    private final SessionFactory sessionFactory;
    public PostRepositoryImpl() {
        sessionFactory = SessionRunner.sessionFactory();
    }

    @Override
    @Transactional
    public Post save(Post post) {
        Transaction transaction = null;
        Post res = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            res = session.get(Post.class, session.save(post));
            transaction.commit();
        } catch (Exception e) {
            Optional.ofNullable(transaction).ifPresent(EntityTransaction::rollback);
        }
        return res;
    }

    @Override
    @Transactional
    public Post update(Post post) {
        if (post != null) {
            EntityManager manager = sessionFactory.createEntityManager();
            manager.getTransaction().begin();
            int res = manager.createQuery("update Post p set p.content=:content, p.created=:created, p.updated=:updated where id=:id")
                    .setParameter("content", post.getContent())
                    .setParameter("created", post.getCreated())
                    .setParameter("updated", post.getUpdated())
                    .setParameter("id", post.getId()).executeUpdate();
            manager.getTransaction().commit();
            return res >= 1 ? post : null;
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(Integer integer) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        boolean res = entityManager.createQuery("delete from Post where id in (:id)")
                .setParameter("id", integer)
                .executeUpdate() >= 1;
        entityManager.getTransaction().commit();
        return res;
    }

    @Override
    public Post findById(Integer integer) {
        Session session = sessionFactory.openSession();
        Post post = session.find(Post.class, integer);
        session.beginTransaction().commit();
        return post;
    }

    @Override
    public List<Post> getAll() {
        Session session = sessionFactory.openSession();
        List<Post> posts = session.createQuery("from Post ", Post.class).getResultList();
        session.beginTransaction().commit();
        return posts;
    }

    @Override
    public List<Post> findByWriter(Writer writer) {
        Session session = sessionFactory.openSession();
        List<Post> posts = session.createQuery("from Post where writer.id = :id", Post.class)
                .setParameter("id", writer.getId())
                .getResultList();
        session.beginTransaction().commit();
        return posts;
    }
}
