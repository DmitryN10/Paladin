package com.netcracker.paladin.infrastructure.repositories;

import com.netcracker.paladin.domain.PublicKeyEntry;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ivan on 27.11.16.
 */
public class PublicKeyEntryRepositoryImpl implements PublicKeyEntryRepository{

    private final DataSource dataSource;

    public PublicKeyEntryRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(PublicKeyEntry PublicKeyEntry){

        String sql = "INSERT INTO PUBLICKEYS " + "(EMAIL, PUBLICKEY) VALUES (?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, PublicKeyEntry.getEmail());
            ps.setBytes(2, PublicKeyEntry.getOwnPublicKey());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    public PublicKeyEntry findByEmail(String email){

        String sql = "SELECT * FROM PUBLICKEYS WHERE EMAIL = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            PublicKeyEntry PublicKeyEntry = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PublicKeyEntry = new PublicKeyEntry(
                    rs.getString("EMAIL"),
                    rs.getBytes("PUBLICKEY")
                );
            }
            rs.close();
            ps.close();
            return PublicKeyEntry;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }
}
