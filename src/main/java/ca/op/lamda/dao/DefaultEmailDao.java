package ca.op.lamda.dao;

import ca.op.lamda.db.Database;
import ca.op.lamda.model.Email;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public enum DefaultEmailDao implements EmailDao {
    INSTANCE;

    @Override
    public List<Email> findAll() {
        final List<Email> emailList = new ArrayList<>();

        try (Connection conn = Database.connection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM data")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Email email = Email.of(
                        rs.getLong("id"),
                        rs.getString("to_address"),
                        rs.getString("subject"),
                        rs.getString("message_type"),
                        rs.getString("domain"),
                        rs.getString("from_address"),
                        rs.getString("mail_date_time"),
                        rs.getString("sns_publish_time"),
                        rs.getString("ses_message_id"));

                emailList.add(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emailList;
    }


    @Override
    public void insert(Email email) {
        try (Connection conn = Database.connection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO data " +
                     "(to_address, subject, message_type, domain, from_address, mail_date_time, " +
                     "sns_publish_time, ses_message_id) VALUES (?,?,?,?,?,?,?,?)")) {

            ps.setString(1, email.getToAddress());
            ps.setString(2, email.getSubject());
            ps.setString(3, email.getMessageType());
            ps.setString(4, email.getDomain());
            ps.setString(5, email.getFromAddress());
            ps.setString(6, email.getMailDateTime());
            ps.setString(7, email.getSnsPublishTime());
            ps.setString(8, email.getSesMessageId());

            int rs = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
