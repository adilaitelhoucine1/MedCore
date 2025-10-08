import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestJPAConnection {
    public static void main(String[] args) {
        try {
            // Name must match your persistence-unit name in persistence.xml ("default")
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
            System.out.println("JPA connected to database!");
            emf.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }
    }
}