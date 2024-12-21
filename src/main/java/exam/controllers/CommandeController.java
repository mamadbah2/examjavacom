package exam.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder.In;

import exam.entities.Article;
import exam.entities.Client;
import exam.entities.Commande;
import exam.entities.DetailArticleCommande;
import exam.repositories.ArticleRepository;
import exam.repositories.ClientRepository;
import exam.repositories.CommandeRepository;
import exam.repositories.DetailArticleCommandeRepository;
import exam.repositories.impl.ArticleRepositoryImpl;
import exam.repositories.impl.ClientRepositoryImpl;
import exam.repositories.impl.CommandeRepositoryImpl;
import exam.repositories.impl.DetailArticleCommandeRepositoryImpl;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class CommandeController {

    @FXML
    private TextField clientTelephone;

    @FXML
    private DatePicker commandetDate;
    @FXML
    private TextField commandetAmount;

    @FXML
    private TableView<DetailArticleCommande> commandetArticlesTable;

    @FXML
    private TableColumn<DetailArticleCommande, String> colLibelle;

    @FXML
    private TableColumn<DetailArticleCommande, Integer> colQte;
    @FXML
    private TableColumn<DetailArticleCommande, Integer> colPrix;
    @FXML
    private HBox hbox;

    @FXML
    private Button addArticle;

    @FXML
    private Button createCommande;

    @FXML
    private TextField articleName;

    @FXML
    private TextField articleQuantity;

    @FXML
    private TextField articlePrice;

    @FXML
    private Button btnAjout;

    @FXML
    private Button btnEnregistrer;



    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionCommande");
    private EntityManager em = emf.createEntityManager();

    // ------------------------Repositories----------------------------------------------
    private ArticleRepository articleRepository ;
    private CommandeRepository commandeRepository ;
    private DetailArticleCommandeRepository detailArticleCommandeRepository ;
    private ClientRepository clientRepository ;
    // -----------------------------------------------------------------------------------

    public CommandeController() {
        this.em = emf.createEntityManager();
        this.articleRepository = new ArticleRepositoryImpl();
        this.commandeRepository = new CommandeRepositoryImpl();
        this.detailArticleCommandeRepository = new DetailArticleCommandeRepositoryImpl();
        this.clientRepository = new ClientRepositoryImpl();
    }



     @FXML
    public void initialize() {
       colLibelle.setCellValueFactory(cellData -> {
            DetailArticleCommande detailArticleCommande = cellData.getValue();
            if (detailArticleCommande != null && detailArticleCommande.getArticle() != null) {
                return new SimpleStringProperty(detailArticleCommande.getArticle().getLibelle());
            }
            return new SimpleStringProperty("");
        });
        colQte.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        

    }

    public void addArticleTocommandet() {
        String name = articleName.getText();
        TypedQuery<Article> query = em.createQuery("SELECT a FROM Article a WHERE a.libelle = :name", Article.class);
        query.setParameter("name", name);
        Article article = query.getSingleResult();

        if (article != null) {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Article trouvé : " + article.toString());
            System.out.println("-----------------------------------------------------------------------");
            // Créer un nouveau détail de dette
            DetailArticleCommande debtArticlesTable = new DetailArticleCommande();
            debtArticlesTable.setArticle(article);
            debtArticlesTable.setQuantity(Integer.parseInt(articleQuantity.getText()));
            debtArticlesTable.setPrix(article.getPrix() * debtArticlesTable.getQuantity());
            
            // Ajouter le détail de dette à la table des articles de la dette
            commandetArticlesTable.getItems().add(debtArticlesTable);

            // Réinitialiser les champs de saisie
            articleName.clear();
            articleQuantity.clear();
            articlePrice.clear();

        } else {
            // Gérer le cas où aucun article n'est trouvé
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Aucun article trouvé avec ce nom.");
            System.out.println("-----------------------------------------------------------------------");
            // em.getTransaction().rollback();
        }
    }

    public void createcommande() {
        em.getTransaction().begin();
        try {
            // Recherche du client
            String phone = clientTelephone.getText();
            TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.telephone = :phone",
                    Client.class);
            query.setParameter("phone", phone);
            Client client = query.getSingleResult();

            if (client != null) {
                // Création de la dette
                Commande commande = new Commande();
                commande.setDate(java.sql.Date.valueOf(commandetDate.getValue()));

                // Création et calcul des détails de la dette
                List<DetailArticleCommande> detailArticleCommande = new ArrayList<>();
                double totalAmount = 0.0;

                for (DetailArticleCommande item : commandetArticlesTable.getItems()) {
                    DetailArticleCommande detail = new DetailArticleCommande();
                    detail.setCommande(commande);
                    detail.setArticle(item.getArticle());
                    detail.setQuantity(item.getQuantity());
                    detail.setPrix(item.getPrix());
                    totalAmount += detail.getPrix();
                    detailArticleCommande.add(detail);
                }

                // Configuration de la dette avec le montant total calculé
                commande.setAmount(totalAmount);
                commande.setClient(client);
                commande.setDetailArticleCommandes(detailArticleCommande);

                // Persister la dette
                em.persist(commande);

                // Persister les détails
                for (DetailArticleCommande detail : detailArticleCommande) {
                    em.persist(detail);
                }

                

                em.getTransaction().commit();
                showAlert("Success", "Commande créée avec succès", Alert.AlertType.INFORMATION);
               
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            System.out.println("Erreur lors de la création de la dette : " + e.getMessage());
        }

    }

    public void search() {
        String phone = clientTelephone.getText();
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.telephone = :phone", Client.class);
        query.setParameter("phone", phone);
        Client client = query.getSingleResult();

        if (client != null) {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Client trouvé : " + client.toString());
            System.out.println("-----------------------------------------------------------------------");
            showAlert("Success", client.toString(), Alert.AlertType.INFORMATION);
            commandetArticlesTable.setDisable(false);
            btnAjout.setDisable(false);
            btnEnregistrer.setDisable(false);
            commandetDate.setDisable(false);
            
        } else {
            showAlert("Erreur", "Aucun client trouvé avec ce numéro de téléphone.", Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
