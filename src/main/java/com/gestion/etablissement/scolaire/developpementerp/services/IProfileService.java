package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.ChangePasswordRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.UpdateProfileRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ProfileResponse;

public interface IProfileService {

    ProfileResponse getProfile(String email);

    ProfileResponse updateProfile(String email, UpdateProfileRequest request);

    void changePassword(String email, ChangePasswordRequest request);
}
