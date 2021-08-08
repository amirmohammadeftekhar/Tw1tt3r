package web.serviceinterfaces;

import dtos.PersonIniDto;
import dtos.PictureDto;
import entities.enums.LastSeenType;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface SettingControllerService {
    @POST(value = "api/setting/categorycreatebuttonaction")
    public Call<Void> categoryCreateButtonAction(@Query("name") String name, @Query("currentPersonId") int currentPersonId, @Body List<Integer> peopleToAdd);

    @POST(value = "api/setting/roomcreatebuttonaction")
    public Call<Void> roomCreateButtonAction(@Query("name") String name, @Query("currentPersonId") int currentPersonId, @Query("isPv") boolean isPv, @Body List<Integer> peopleToAdd);

    @POST(value = "api/setting/changepicture")
    public Call<Void> changePicture(@Query("currentPersonId") int currentPersonId, @Body PictureDto picture);

    @POST(value = "api/setting/updateprofilebuttonaction")
    public Call<Void> updateProfileButtonAction(@Query("currentPersonId") int currentPersonId, @Body PersonIniDto personIniDto);

    @POST(value = "api/setting/lastseentypeupdatebuttonaction")
    public Call<Void> lastSeenTypeUpdateButtonAction(@Query("currentPersonId") int currentPersonId, @Query("lastSeenType")LastSeenType lastSeenType);

    @POST(value = "api/setting/deactivatebuttonaction")
    public Call<Void> deactivateButtonAction(@Query("currentPersonId") int currentPersonId);

    @POST(value = "api/setting/deleteaccountaction")
    public Call<Void> deleteAccountAction(@Query("currentPersonId") int currentPersonId);

    @POST(value = "api/setting/addpersontocategorybuttonaction")
    public Call<Void> addPersonToCategoryButtonAction(@Query("categoryId") int categoryId, @Body List<Integer> peopleToAdd);

    @POST(value = "api/setting/deletecategorybuttonaction")
    public Call<Void> deleteCategoryButtonAction(@Query("currentPersonId") int currentPersonId, @Query("categoryId") int categoryId);

    @POST(value = "api/setting/removepersonfromcategorybuttonaction")
    public Call<Void> removePersonFromCategoryButtonAction(@Query("categoryId") int categoryId, @Body List<Integer> peopleToRemove);
}














