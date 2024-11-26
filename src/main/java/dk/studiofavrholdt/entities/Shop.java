package dk.studiofavrholdt.entities;

import java.util.List;

public class Shop {
    private int id;
    private String name;
    private String url;
    private List<String> categories;

    public Shop() {
    }

    public Shop(int id, String name, String url, List<String> categories) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", categories=" + categories +
                '}';
    }
}