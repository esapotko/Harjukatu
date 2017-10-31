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
public interface Harjukatu {
    static enum MESSAGE{OK,ERROR};
    static int CLAMD = 3310;
    static int MINUTE = 60000;
    static String FILEPATH = "/var/www/html/static/";
    static String IMAGEPATTERN = "(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP)$";
    static String SLIDEURL = "http://potkonen.dy.fi/static/";
}
