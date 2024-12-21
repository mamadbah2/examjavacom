module exam {
    // Dépendances JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    
    // Dépendances Hibernate et JPA
    requires java.sql; // Pour JDBC
    requires org.hibernate.orm.core; // Hibernate ORM
    
    // Dépendance pour Lombok
    requires static lombok; // Lombok pour générer le code
    
    // Dépendance pour PostgreSQL
    requires java.persistence; // JPA
    requires org.postgresql.jdbc; // JDBC PostgreSQL
    
    // Ouvrir les packages à JavaFX pour FXML
    opens exam to javafx.fxml;
    opens exam.controllers to javafx.fxml;
    opens exam.entities to org.hibernate.orm.core, javafx.base; // Pour Hibernate/JPA
    
    // Exporter les packages
    exports exam;
    exports exam.controllers;
}
