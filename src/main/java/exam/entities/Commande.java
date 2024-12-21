package exam.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "commandes")
public class Commande {
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Double amount;
    @Temporal(TemporalType.TIMESTAMP)   
    private Date date = new Date();
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<DetailArticleCommande> detailArticleCommandes;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public List<DetailArticleCommande> getDetailArticleCommandes() {
        return detailArticleCommandes;
    }
    public void setDetailArticleCommandes(List<DetailArticleCommande> detailArticleCommandes) {
        this.detailArticleCommandes = detailArticleCommandes;
    }
  
}
