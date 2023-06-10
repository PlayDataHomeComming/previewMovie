package repository;

import Connection.JdbcConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

//디비 접근하는 친구
public class Repository {
    public static boolean preInsert(String movieName, String dateOfPreview) {
        Connection conn = new JdbcConnection().getJdbc();

        String sql = "insert into preview(movie_name, date_of_preview)\n" +
                "values (?, ?)";
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, movieName);
            psmt.setString(2, dateOfPreview);
            if (psmt.executeUpdate() == 0) {
                System.out.println("insertPreview err");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("connection close fail");
        }

        return false;
    }
    public static boolean isDuplicatePreview(Connection conn,String movieName, String dateOfPreview) {
        //시사회 정보 중복되면 데이터 삽입 안되게 코드 작성

        String duplicatePreview = "select count(*) from preview where movie_name = ? and date_of_preview = ?";
        try (PreparedStatement stmt = conn.prepareStatement(duplicatePreview)) {
            stmt.setString(1, movieName);
            stmt.setString(2, dateOfPreview);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void cinInsert(String cinemaName,String address,String numChair){
        Connection conn = new JdbcConnection().getJdbc();
        String sql = "insert into cinema(cinema_name, address,capacity_chair)\n" +
                "values (?, ?,?)";
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, cinemaName);
            psmt.setString(2, address);
            psmt.setString(3, numChair);

            if (psmt.executeUpdate() == 0) {
                System.out.println("insertPreview err");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String select_sql = "select id from cinema " +     // sql 구문에서 where 앞에 띄어쓰기를 해줘야됨
                "where cinema_name=? and address=? and capacity_chair=?";
        Integer cinemaId = null;
        try {
            PreparedStatement psmt = conn.prepareStatement(select_sql);
            psmt.setString(1,cinemaName);
            psmt.setString(2,address);
            psmt.setString(3,numChair);
            ResultSet resultSet = psmt.executeQuery();
            while (resultSet.next()){
                cinemaId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql3 = "insert into chair(x, y,cinema_id)\n" +
                "values (?, ?, ?)";
        int x;
        int y;
        for(int i=0;i<Integer.parseInt(numChair);i++) {
            y=(int)(i/8)+1;
            x=(i%8)+1;
            try {
                PreparedStatement psmt = conn.prepareStatement(sql3);
                psmt.setInt(1, x);
                psmt.setInt(2, y);
                psmt.setInt(3, cinemaId);
                if (psmt.executeUpdate() == 0) {
                    System.out.println("insertPreview err");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("connection close fail");
        }
    }

    public static boolean isDuplicateCinemaName(Connection conn, String cinemaName, String address) {
        //영화관 이름 날짜 중복되면 데이터 삽입 안되게 코드 작성
        String duplicateCinema = "select count(*) from cinema where cinema_name = ? and address = ?";
        try (PreparedStatement stmt = conn.prepareStatement(duplicateCinema)) {
            stmt.setString(1, cinemaName);
            stmt.setString(2, address);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public static void printCinema() {
        Connection conn = new JdbcConnection().getJdbc();
        String sql = "select * from cinema ";
        Integer id=null;
        String cinemaName=null;
        String address=null;
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet res = psmt.executeQuery();
            System.out.println("영화관No.\t영화관 이름\t\t\t지점");
            while (res.next()){
                id=res.getInt("id");
                cinemaName=res.getString("cinema_name");
                address=res.getString("address");
                System.out.printf("%-4d\t\t%-12s%s\n",id,cinemaName,address);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("connection close fail");
        }
    }


    public static void printPreview() {
        Connection conn = new JdbcConnection().getJdbc();
        String sql = "select * from preview";
        Integer id=null;
        String movieName=null;
        String dateOfPreview=null;
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet res = psmt.executeQuery();
            System.out.println("시사회No.\t영화 제목\t\t\t상영 날짜");
            while (res.next()){
                id=res.getInt("id");
                movieName=res.getString("movie_name");
                dateOfPreview=res.getString("date_of_preview");
                System.out.printf("   %-4d\t\t%-13s%s\n",id,movieName,dateOfPreview);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("connection close fail");
        }
    }

    public static void printPerson() {
        Connection conn = new JdbcConnection().getJdbc();
        String sql = "select * from person";
        Integer id=null;
        String name=null;
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet res = psmt.executeQuery();
            System.out.println("id\tname");
            while (res.next()){
                id=res.getInt("id");
                name=res.getString("name");
                System.out.printf("%-4d%s\n",id,name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("connection close fail");
        }
    }

    public static void insertCombinePreview(String previewId,String cinemaId){
        Connection conn = new JdbcConnection().getJdbc();
        String sql = "insert into preview_cinema(preview_id, cinema_id)\n" +
                "values (?, ?)";
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, previewId);
            psmt.setString(2, cinemaId);
            if (psmt.executeUpdate() == 0) {
                System.out.println("insertPreview err");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("connection close fail");
        }
    }

    public static void relatedCinemaPrint(String previewId){
        Connection conn = new JdbcConnection().getJdbc();
        String sql = "select * from (select * from cinema,preview_cinema where cinema.id=preview_cinema.cinema_id)as t where preview_id=?";
        Integer id=null;
        String cinemaName=null;
        String address=null;
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1,previewId);
            ResultSet res = psmt.executeQuery();
            System.out.println("영화관No.\t영화관 이름\t\t  지점");
            while (res.next()){
                id=res.getInt("id");
                cinemaName=res.getString("cinema_name");
                address=res.getString("address");
                System.out.printf("  %-4d\t    %-14s%s\n",id,cinemaName,address);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("connection close fail");
        }
    }
    public static void printChair(String cinemaId) {
        Connection conn = new JdbcConnection().getJdbc();
        String sql = "select * from chair where cinema_id = ?";

        Map<String, Map<Integer, String>> chairMap = new HashMap<>();

        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, cinemaId);
            ResultSet res = psmt.executeQuery();

            // 좌석 정보를 행과 열로 나누어 저장
            while (res.next()) {
                int x = res.getInt("x");
                int y = res.getInt("y");
                String status = res.getString("status");
                String row = String.valueOf((char) ('A' + (y - 1))); // 행
                String seat = String.valueOf(x); // 열

                if (!chairMap.containsKey(row)) {
                    chairMap.put(row, new HashMap<>());
                }
                chairMap.get(row).put(x, status);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 행-열 좌석 맵을 이용하여 출력
        System.out.println("\t\t\t SCREEN");
        for (char row = 'A'; row <= 'H'; row++) { // A부터 H까지 행
            System.out.print(row + " ");
            for (int col = 1; col <= 8; col++) { // 1부터 6까지 열
                String chair;
                if (chairMap.containsKey(String.valueOf(row))) {
                    chair = chairMap.get(String.valueOf(row)).get(col);
                    if (chair == null) {
                        chair = " ";
                    }
                } else {
                    chair = " ";
                }
                System.out.printf("%-4s", chair);
            }
            System.out.println();
        }

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("connection close fail");
        }
    }

    public static void changeStatusAndPersonInsert(String chairId,String name,String phoneNum){
        Connection conn = new JdbcConnection().getJdbc();
        String sql = "update chair set status=\'x\' where id=?";
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, chairId);
            if (psmt.executeUpdate() == 0) {
                System.out.println("updateChair err");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql5 = "insert into person(chair_id, name,phone_num)\n" +
                "values (?, ?, ?)";
        try {
            PreparedStatement psmt = conn.prepareStatement(sql5);
            psmt.setString(1, chairId);
            psmt.setString(2, name);
            psmt.setString(3, phoneNum);
            if (psmt.executeUpdate() == 0) {
                System.out.println("insertPreview err");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("connection close fail");
        }
    }
}

