package com.databases.shop.services.implementations;

import com.databases.shop.services.interfaces.AdminService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class AdminServiceImpl implements AdminService {

    @Override
    public void deleteUserAccountByEmail(String email) throws Exception {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getUserByEmail(email).getUid();
        firebaseAuth.deleteUser(uid);
    }

    @Override
    public void registerUser(String email, String password) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setDisabled(false);
        FirebaseAuth.getInstance().createUser(request);
    }


    @Override
    public void saveUserToFirestore(String email, String role) {
        Firestore db = FirestoreClient.getFirestore();

        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("role", role);

        db.collection("users").add(data);
    }

    @Override
    public void deleteUserFromFirestore(String email) {
        Firestore db = FirestoreClient.getFirestore();

        CollectionReference users = db.collection("users");
        Query query = users.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                users.document(document.getId()).delete();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
