package pl.machnikovsky.internexercise.controller;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.machnikovsky.internexercise.model.RepositoryEntity;

import java.util.ArrayList;
import java.util.List;

public class MockValues {

    public static ResponseEntity<List<RepositoryEntity>> getJsonObjects() {
        List<RepositoryEntity> jsonObjects = new ArrayList<>();

        jsonObjects.add(new RepositoryEntity("my_repo", 14));
        jsonObjects.add(new RepositoryEntity("another_repo", 3));
        jsonObjects.add(new RepositoryEntity("repo3", 35));
        jsonObjects.add(new RepositoryEntity("different_repo", 42));

        return new ResponseEntity(jsonObjects, HttpStatus.OK);
    }
}
