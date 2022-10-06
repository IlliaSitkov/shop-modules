package com.databases.shop.services.interfaces;

public interface AdminService {

    void deleteUserAccountByEmail(String email) throws Exception;
    void registerUser(String email,String password) throws Exception;
    void saveUserToFirestore(String email, String role);
    void deleteUserFromFirestore(String email);

}
