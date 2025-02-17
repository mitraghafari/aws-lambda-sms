package ca.op.lamda.dao;

import ca.op.lamda.model.Sms;

import java.util.List;

public interface SmsDao {

    List<Sms> findAll();

    void insert(Sms sms);
}
