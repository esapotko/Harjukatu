/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.dy.potkonen.harjukatu.domain;

/**
 *
 * @author esa
 */
public class Post {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    Integer id;

    private String Title;
    private String Url;
    private String Description;
    private String Banner;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getBanner() {
        return Banner;
    }

    public void setBanner(String Banner) {
        this.Banner = Banner;
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", Title=" + Title + ", Url=" + Url + ", Description=" + Description + ", Banner=" + Banner + '}';
    }
    
}
