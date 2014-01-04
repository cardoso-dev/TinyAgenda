/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tinyagenda.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Pedro
 */
@Entity
public class Event extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date myDay;
    private Time anHour;
    private String name;
    private String description;
    @OneToOne
    private Person user;

    public Event(){
    }
    
    public Event(Date myDay, Time anHour, String name, String descrip, Person usr) {
        this.myDay=myDay;
        this.anHour=anHour;
        this.name=name;
        this.description=descrip;
        this.user=usr;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMyday(Date myDay) {
        this.myDay = myDay;
    }

    public void setAnhour(Time anHour) {
        this.anHour = anHour;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(Person user){
        this.user=user;
    }

    public Date getMyday() {
        return myDay;
    }

    public Time getAnhour() {
        return anHour;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Person getUser() {
        return user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tinyagend.model.Event[ id=" + id + " ]";
    }
    
}
