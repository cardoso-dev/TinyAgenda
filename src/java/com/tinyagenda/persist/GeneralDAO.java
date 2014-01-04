/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tinyagenda.persist;

import com.tinyagenda.model.BaseEntity;
import com.tinyagenda.model.Person;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 *
 * @author Pedro
 */
public class GeneralDAO {
    
    private String error;
    private static final SessionFactory sessionFactory;
    private static final ServiceRegistry serviceRegistry;
    private static final Configuration configuration;
    
    static{
        try{
            configuration=new Configuration().configure();
            serviceRegistry=(ServiceRegistry) new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry(); 
            sessionFactory=configuration.buildSessionFactory(serviceRegistry);
        }catch(HibernateException he){
            System.err.println("Ocurrió un error en la inicialización de la SessionFactory: " + he);
            //he.printStackTrace();
            throw new ExceptionInInitializerError(he);
        }
    }
    
    public GeneralDAO(){
    }
    
    private Session getSession(){
        Session ses=sessionFactory.openSession();
        return ses;
    }
    
    private Transaction getTransaction(Session ses){
        Transaction trx=ses.beginTransaction();
        return trx;
    }
    
    public boolean saveEntity(BaseEntity entity){
        boolean saveok=true;
        Session ses=getSession();
        Transaction trx=null;
        try{
            trx=getTransaction(ses);
            if(entity.getId()==null || entity.getId()==0){
                ses.save(entity);
            }else{
                ses.update(entity);    
            }
            trx.commit();
        }catch(Exception he){
            trx.rollback();
            error="ERROR: "+he.getMessage();
            //he.printStackTrace();
            saveok=false;
        }
        return saveok;
    }
    
    public boolean deleteEntity(BaseEntity entity){
        boolean deleted=true;
        Session ses=getSession();
        Transaction trx=null;
        try{
            trx=getTransaction(ses);
            ses.delete(entity);
            trx.commit();
        }catch(Exception he){
            trx.rollback();
            error="ERROR: "+he.getMessage();
            //he.printStackTrace();
            deleted=false;
        }
        return deleted;
    }
    
    public boolean deleteEntities(List<BaseEntity> entities){
        boolean deleted=true;
        Session ses=getSession();
        Transaction trx=null;
        try{
            trx=getTransaction(ses);
            for(BaseEntity entity: entities){
                ses.delete(entity);
            }
            trx.commit();
        }catch(Exception he){
            trx.rollback();
            error="ERROR: "+he.getMessage();
            //he.printStackTrace();
            deleted=false;
        }
        return deleted;
    }
    
    public boolean saveEntities(List<BaseEntity> entities){
        boolean deleted=true;
        Session ses=getSession();
        Transaction trx=null;
        try{
            trx=getTransaction(ses);
            for(BaseEntity entity: entities){
                ses.save(entity);
            }
            trx.commit();
        }catch(Exception he){
            trx.rollback();
            error="ERROR: "+he.getMessage();
            //he.printStackTrace();
            deleted=false;
        }
        return deleted;
    }
        
    public BaseEntity getEntity(String qry){
        Query query;
        BaseEntity ent;
        Session ses=getSession();
        try{
            query=ses.createQuery(qry);
            ent=(BaseEntity) query.uniqueResult();
        }catch(Exception ex){
            error=ex.getMessage();
            //ex.printStackTrace();
            ent=null;
        }
        return ent;
    }
    
    public List getListEntities(String fquery){
        List lista;
        Query query;
        Query count;
        Long cRecs;
        Session ses=getSession();
        try{
            query=ses.createQuery(fquery);
            lista=query.list();
        }catch(Exception ex){
            error=ex.getMessage();
            //ex.printStackTrace();
            lista=null;
        }
        return lista;
    }
    
    public List<Object> getListEvents(int year, int month, int day, Person user){
        List<Object> resulset;
        String sentence;
        Query query;
        Session ses=getSession();
        Calendar cal=Calendar.getInstance();
        try{
            cal.set(year, month, day);
            sentence="select e from Event e where myday=:theDay and user=:usr";
            query=ses.createQuery(sentence);
            query.setParameter("theDay",cal.getTime());
            query.setParameter("usr",user);
            resulset=query.list();
        }catch(Exception ex){
            error=ex.getMessage();
            //ex.printStackTrace();
            resulset=null;
        }
        return resulset;
    }
    
    public TreeMap<Integer,Integer> getCountEvents(int month, int year, int monthDays, Person user){
        TreeMap<Integer,Integer> map;
        List<Object[]> resulset;
        String sentence;
        Query count;
        Session ses=getSession();
        Calendar cal=Calendar.getInstance();
        Date fDay;
        Date lDay;
        try{
            map=new TreeMap<Integer,Integer>();
            cal.set(year, month, 1);
            fDay=cal.getTime();
            cal.set(year, month, monthDays);
            lDay=cal.getTime();
            sentence="select myday, count(myday) from Event where myday>=:firstDay and myday<=:lastDay and ";
            sentence+="user=:usr group by myday order by myday";
            count=ses.createQuery(sentence);
            count.setParameter("firstDay",fDay);
            count.setParameter("lastDay",lDay);
            count.setParameter("usr",user);
            resulset=count.list();
            if(!resulset.isEmpty()){
                for(Object[] objRow: resulset){
                    cal.setTime( ((Date)objRow[0]) );
                    map.put( cal.get(Calendar.DATE),Integer.parseInt(objRow[1].toString()) );
                }
            }
        }catch(Exception ex){
            error=ex.getMessage();
            //ex.printStackTrace();
            map=null;
        }
        return map;
    }
    
    public String getError(){
        return error;
    }
    
}

