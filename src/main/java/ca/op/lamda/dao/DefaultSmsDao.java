package ca.op.lamda.dao;

import ca.op.lamda.db.Database;
import ca.op.lamda.model.Sms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public enum DefaultSmsDao implements SmsDao {
    INSTANCE;

    @Override
    public List<Sms> findAll() {
        final List<Sms> smsList = new ArrayList<>();

        try (Connection conn = Database.connection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM sms")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sms sms = Sms.of(
                        rs.getLong("id"),
                        rs.getString("client_op_id"),
                        rs.getString("message"),
                        rs.getTimestamp("create_date")
                );

                smsList.add(sms);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return smsList;
    }


    @Override
    public void insert(Sms sms) {
        try (Connection conn = Database.connection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO sms " +
                     "(client_op_id, message, create_date) VALUES (?,?,?)")) {

            ps.setString(1, sms.getClientOpttId());
            ps.setString(2, sms.getMessage());
            ps.setTimestamp(3, new Timestamp(sms.getCreateDate().getTime()));

            int rs = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
