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
public class Slide {
    private String image;
    private String description;
    public Slide(String name,String description) {
        setImage(name);
        setDescription(description);
    }

    public Slide() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
