package com.example.starsgallery.service;

import com.example.starsgallery.beans.Star;
import com.example.starsgallery.dao.IDao;
import java.util.ArrayList;
import java.util.List;

public class StarService implements IDao<Star> {
    private List<Star> starList;
    private static StarService instance;

    private StarService() {
        starList = new ArrayList<>();
        injectData();
    }

    public static synchronized StarService getInstance() {
        if (instance == null) instance = new StarService();
        return instance;
    }

    private void injectData() {
        starList.add(new Star("Emma Watson", "https://upload.wikimedia.org/wikipedia/commons/7/7f/Emma_Watson_2013.jpg", 4.5f));
        starList.add(new Star("Tom Cruise", "https://upload.wikimedia.org/wikipedia/commons/c/c4/CROP_Tom_Cruise_during_the_2025_Cannes_Film_Festival_03.png", 4.0f));
        starList.add(new Star("Scarlett Johansson", "https://upload.wikimedia.org/wikipedia/commons/a/ad/Scarlett_Johansson-8588.jpg", 4.8f));
        starList.add(new Star("Leonardo DiCaprio", "https://fr.web.img4.acsta.net/c_310_420/img/1e/57/1e57fa9694416c401ca303a0297752bf.jpg", 4.9f));
    }

    @Override public boolean create(Star o) { return starList.add(o); }
    @Override public boolean update(Star o) {
        for (Star s : starList) {
            if (s.getId() == o.getId()) {
                s.setNom(o.getNom());
                s.setNote(o.getNote());
                return true;
            }
        }
        return false;
    }
    @Override public boolean delete(Star o) { return starList.remove(o); }
    @Override public Star findById(int id) {
        for (Star s : starList) if (s.getId() == id) return s;
        return null;
    }
    @Override public List<Star> findAll() { return starList; }
}