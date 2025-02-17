package ca.op.lamda.dao;

import ca.op.lamda.model.Email;

import java.util.List;

public interface EmailDao {

    List<Email> findAll();

    void insert(Email email);
}
