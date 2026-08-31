package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.BulletinRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.BulletinResponce;

import java.util.List;

public interface IBulletinService {
    BulletinResponce addBulletin(BulletinRequest bulletinRequest);
    BulletinResponce updateBulletin(Long idBulletin, BulletinRequest bulletinRequest);
    void deleteBulletin(Long idBulletin);
    List<BulletinResponce> getAllBulletins();
    BulletinResponce getBulletinById(Long idBulletin);
}
