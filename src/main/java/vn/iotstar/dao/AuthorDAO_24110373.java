package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.models.Author_24110373;

public interface AuthorDAO_24110373 {
    List<Author_24110373> findAll();
    Author_24110373 findById(int id);
    int insert(Author_24110373 author_24110373);
}
