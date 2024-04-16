package harisbrulicita2024.microservice1.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import harisbrulicita2024.microservice1.model.Job;
import harisbrulicita2024.microservice1.dao.JobRepository;
import java.io.Serializable;

@Entity
@Table(name = "jobs", schema = "jobs")
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jobs_id")
    private Integer id;

    @Column(name = "name")
    String job;

    @Column(name = "company")
    String company;

    @Column(name = "city")
    String city;

    @Column(name = "country")
    String country;

    @Column(name = "category")
    String category;

    @Column(name = "pay")
    Integer pay;

    @Column(name = "workinghours")
    String workinghours;

    @Column(name = "office")
    Boolean office;

    @Column(name = "remote")
    Boolean remote;

    public Job() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public String getWorkinghours() {
        return workinghours;
    }

    public void setWorkinghours(String workinghours) {
        this.workinghours = workinghours;
    }

    public Boolean getOffice() {
        return office;
    }

    public void setOffice(Boolean office) {
        this.office = office;
    }

    public Boolean getRemote() {
        return remote;
    }

    public void setRemote(Boolean remote) {
        this.remote = remote;
    }
}