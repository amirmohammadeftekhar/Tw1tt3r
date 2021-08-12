package database;

import entity.MessageToSendEntity;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DataBaseUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(MessageToSendEntity.class);
        return(configuration.buildSessionFactory(new StandardServiceRegistryBuilder().build()));
    }
}
