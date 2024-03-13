package harisbrulicita2024.microservice1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Jobs")
public class Job {

    @Id
    Integer id;
    String job;
    String company;
    String city; // Promijenjeno ime varijable place u city
    String category;
    Integer pay;
    String workinghours;
    Boolean office;
    Boolean remote;
    String country;


    public Job() {}

    public Job(Integer id, String job, String company, String city, String category, Integer pay, String workinghours, Boolean office, Boolean remote, String country) {
        this.id = id;
        this.job = job;
        this.company = company;
        this.city = city; // Promijenjeno ime varijable place u city
        this.category = category;
        this.pay = pay;
        this.workinghours = workinghours;
        this.office = office;
        this.remote = remote;
        this.country = country;
    }

    // Getters i Setters
    // Primer:
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
        return city; // Promijenjeno ime metode getPlace u getCity
    }

    public void setCity(String city) {
        this.city = city; // Promijenjeno ime metode setPlace u setCity
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
        return workinghours; // Promijenjeno ime metode getworkinghours u getWorkinghours
    }

    public void setWorkinghours(String workinghours) {
        this.workinghours = workinghours; // Promijenjeno ime metode setworkinghours u setWorkinghours
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setName(String softwareDeveloper) {
    }

    public Object getName() {
        return null;
    }
}
