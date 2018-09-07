package util;

import bean.TicketInfo;
import config.Config;

import java.sql.*;

public class DBUtil {
    static Connection conn;
    static String dbName = "ticket.db";

    public static void init() {
        // 数据库的用户名与密码，需要根据自己的设置
        final String DB_URL = "jdbc:sqlite:" + Config.BASE_PATH + "\\" + dbName;
        try {
            // 注册 JDBC 驱动
            Class.forName("org.sqlite.JDBC");

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static TicketInfo isExist(TicketInfo ticketInfo) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "select * from ticket where code =" + ticketInfo.getCode() + " and number =" + ticketInfo.getNumber();
        System.out.println(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        if (!resultSet.next()) {
            statement.close();
            return null;
        } else {
            String code = resultSet.getString("code");
            String number = resultSet.getString("number");
            String hashcode = resultSet.getString("hashcode");
            String picPath = resultSet.getString("picPath");
            Date date = resultSet.getDate("transactionDate");
            statement.close();
            return new TicketInfo(code, number, hashcode, date, picPath);
        }
    }

    public static void save(TicketInfo ticketInfo) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "insert into ticket (code,number,hashcode,transactionDate,picPath) values(" + ticketInfo.getCode() + "," + ticketInfo.getNumber()
                + "," + ticketInfo.getHashcode() + "," + ticketInfo.getTransactionDate().getTime() + ",'" + ticketInfo.getPicPath() + "')";
        System.out.println(sql);
        statement.execute(sql);
        statement.close();
    }

    public static void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
