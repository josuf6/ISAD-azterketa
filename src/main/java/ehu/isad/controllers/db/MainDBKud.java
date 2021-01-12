package ehu.isad.controllers.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainDBKud {

    private static final MainDBKud nMainDBKud = new MainDBKud();

    public static MainDBKud getMainDBKud() {
        return nMainDBKud;
    }

    private MainDBKud() {}

    public boolean md5Dago(String md5) {
        String query = "select md5 from checksums where md5='" + md5 +"'";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        try {
            if (rs.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String versionLortu(String md5) {
        String version = "";
        String query = "select version from checksums where md5='" + md5 +"'";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        try {
            if (rs.next()) {
                version = rs.getString("version");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return version;
    }

    public void gordeMd5(String md5, String bertsioBerria) {
        String query = "insert into checksums " +
                "values(1,'" + bertsioBerria + "','" + md5 + "','\"README\"')";
        DBKud.getDBKud().execSQL(query);
    }

    public void versionEguneratu(String md5, String bertsioBerria) {
        String query = "update checksums set version='" + bertsioBerria + "' " +
                "where md5='" + md5 + "'";
        DBKud.getDBKud().execSQL(query);
    }
}