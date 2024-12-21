package exam.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 50, unique = true)
    private String libelle;

    private int qte_stock;
    private int prix;

    @OneToMany(mappedBy = "article")
    private List<DetailArticleCommande> detailArticleCommandes;

    @Override
    public String toString() {
        return "Article [id=" + id + ", libelle=" + libelle + ", qteStock=" + qte_stock + ", prix=" + prix + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getQte_stock() {
        return qte_stock;
    }

    public void setQte_stock(int qte_stock) {
        this.qte_stock = qte_stock;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public List<DetailArticleCommande> getDetailArticleCommandes() {
        return detailArticleCommandes;
    }

    public void setDetailArticleCommandes(List<DetailArticleCommande> detailArticleCommandes) {
        this.detailArticleCommandes = detailArticleCommandes;
    }

}