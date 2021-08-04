package database;

import entity.MessageEntity;
import entity.MessageToSendEntity;
import entity.RoomEntity;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DataBaseUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(MessageEntity.class);
        configuration.addAnnotatedClass(MessageToSendEntity.class);
        configuration.addAnnotatedClass(RoomEntity.class);
        return(configuration.buildSessionFactory(new StandardServiceRegistryBuilder().build()));
    }
}
