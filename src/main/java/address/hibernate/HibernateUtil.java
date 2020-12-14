package address.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(address.model.Contact.class);
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        configuration.configure(new File("src/main/resources/hibernate.cfg.xml"));

        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
