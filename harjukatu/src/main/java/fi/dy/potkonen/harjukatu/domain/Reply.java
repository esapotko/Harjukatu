/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.dy.potkonen.harjukatu.domain;

import fi.dy.potkonen.harjukatu.domain.Harjukatu;

/**
 *
 * @author esa
 */
public class Reply {
    Harjukatu.MESSAGE type = Harjukatu.MESSAGE.OK;
    String message = "";
    UploadItem item;
    
    public Reply(Harjukatu.MESSAGE msg, String message) {
        type = msg;
        this.message = message;
    }
    
    public String toString() {
        String json = "{\"" + type + "\":\"" + message + "\"}";
        return json;
    }

    public Harjukatu.MESSAGE getType() {
        return type;
    }

    public void setType(Harjukatu.MESSAGE type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void addMessage(String message) {
        this.message += message + "\n";
    }

    public UploadItem getItem() {
        return item;
    }

    public void setItem(UploadItem item) {
        this.item = item;
    }
}
