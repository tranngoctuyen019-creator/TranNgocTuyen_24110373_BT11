package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.AuthorDAO_24110373;
import vn.iotstar.dao.impl.AuthorDAOImpl_24110373;
import vn.iotstar.models.Author_24110373;
import vn.iotstar.service.AuthorService_24110373;

public class AuthorServiceImpl_24110373 implements AuthorService_24110373 {

    private final AuthorDAO_24110373 authorDAO_24110373 = new AuthorDAOImpl_24110373();

    @Override
    public List<Author_24110373> getAll() {
        return authorDAO_24110373.findAll();
    }
}
