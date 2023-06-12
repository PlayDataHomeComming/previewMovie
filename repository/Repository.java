package repository;

import Connection.JdbcConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

    public static boolean isDuplicatePreview(Connection conn, String movieName, String dateOfPreview) {
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

    public static void cinInsert(String cinemaName, String address, String numChair) {
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
        Integer id = null;
        String cinemaName = null;
        String address = null;
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet res = psmt.executeQuery();
            System.out.println("영화관No.\t영화관 이름\t\t\t지점");
            while (res.next()) {
                id = res.getInt("id");
                cinemaName = res.getString("cinema_name");
                address = res.getString("address");
                System.out.printf("%-4d\t\t%-12s%s\n", id, cinemaName, address);
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
        Integer id = null;
        String movieName = null;
        String dateOfPreview = null;
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet res = psmt.executeQuery();
            System.out.println("시사회No.\t영화 제목\t\t\t상영 날짜");
            while (res.next()) {
                id = res.getInt("id");
                movieName = res.getString("movie_name");
                dateOfPreview = res.getString("date_of_preview");
                System.out.printf("   %-4d\t\t%-13s%s\n", id, movieName, dateOfPreview);
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
        Integer id = null;
        String name = null;
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet res = psmt.executeQuery();
            System.out.println("id\tname");
            while (res.next()) {
                id = res.getInt("id");
                name = res.getString("name");
                System.out.printf("%-4d%s\n", id, name);
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
        sql = "select * from (select * from cinema,preview_cinema where cinema.id=preview_cinema.cinema_id)as t "
                +"where preview_id=? and cinema_id=?";
        Integer previewCinemaId=null;
        Integer numChair=null;
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1,previewId);
            psmt.setString(2,cinemaId);
            ResultSet res = psmt.executeQuery();
            while (res.next()){
                previewCinemaId=res.getInt("pc_id");
                numChair=res.getInt("capacity_chair");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sql = "insert into chair(x, y,preview_cinema_id)\n" +
                "values (?, ?, ?)";
        int x;
        int y;
        for(int i = 0; i<numChair; i++) {
            y=(int)(i/5)+1;
            x=(i%5)+1;
            try {
                PreparedStatement psmt = conn.prepareStatement(sql);
                psmt.setInt(1, x);
                psmt.setInt(2, y);
                psmt.setInt(3, previewCinemaId);
                if (psmt.executeUpdate() == 0) {
                    System.out.println("insertChair err");
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
    public static void relatedCinemaPrint(String previewId){
        Connection conn = new JdbcConnection().getJdbc();
        String sql = "select * from (select * from cinema,preview_cinema where cinema.id=preview_cinema.cinema_id)as t where preview_id=?";
        Integer pcId=null;
        String cinemaName=null;
        String address=null;
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1,previewId);
            ResultSet res = psmt.executeQuery();
            System.out.println("영화관No.\t영화관 이름\t\t  지점");
            while (res.next()){
                pcId=res.getInt("pc_id");
                cinemaName=res.getString("cinema_name");
                address=res.getString("address");
                System.out.printf("%-7d%-14s%s\n",pcId,cinemaName,address);
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

    public static void relatedPreviewPrint(String cinemaId){
        Connection conn = new JdbcConnection().getJdbc();
        String sql = "select * from (select * from preview,preview_cinema where preview.id=preview_cinema.preview_id)as t where cinema_id=?";
        Integer pcId=null;
        String movieName=null;
        String dateOfPreivew=null;
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1,cinemaId);
            ResultSet res = psmt.executeQuery();
            System.out.println("시사회No. \t영화 제목\t\t\t상영 날짜");
            while (res.next()){
                pcId=res.getInt("pc_id");
                movieName=res.getString("movie_name");
                dateOfPreivew=res.getString("date_of_preview");
                System.out.printf("%-7d%-13s%s\n",pcId,movieName,dateOfPreivew);
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

    public static void printChair(String pcId){
        Connection conn = new JdbcConnection().getJdbc();
        String sql = "select * from chair "+
                "where preview_cinema_id=?";
        //preview_cinema_id로 input
        Integer id=null;
        Integer x=null;
        Integer y=null;
        String status=null;
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1,pcId);//cinema_preview_id 입력한거임
            ResultSet res = psmt.executeQuery();
            System.out.println("id \tx\ty\tstatus");
            while (res.next()){
                id=res.getInt("id");
                x=res.getInt("x");
                y=res.getInt("y");
                status=res.getString("status");
                System.out.printf("%-5d%-4d%-6d%s\n",id,x,y,status);
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

    public static void changeStatusAndPersonInsert(String chairId,String name,String phoneNum){
        Connection conn = new JdbcConnection().getJdbc();
        String checkSql = "select * from chair where id=?";
        String checkChairStatus=null;
        try {
            PreparedStatement psmt = conn.prepareStatement(checkSql);
            psmt.setString(1,chairId);
            ResultSet res = psmt.executeQuery();
            while (res.next()){
                checkChairStatus=res.getString("status");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(checkChairStatus);
        if(checkChairStatus.equals("X")) {
            System.out.println("좌석이 이미 예매되어 있습니다. 다른 좌석 ID를 입력해주세요.");
            Scanner sc = new Scanner(System.in);
            String newChairId;
            boolean isChair = false;
            while (!isChair) {
                System.out.print("ChairId = ");
                newChairId = sc.nextLine();
                String checkSql2 = "select * from chair where id=?";
                String newChairStatus = null;
                try {
                    PreparedStatement psmt = conn.prepareStatement(checkSql2);
                    psmt.setString(1, newChairId);
                    ResultSet res = psmt.executeQuery();
                    while (res.next()) {
                        newChairStatus = res.getString("status");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (newChairStatus.equals("X")) {
                    System.out.println("좌석이 이미 예매되어 있습니다. 다른 좌석을 선택해주세요.");
                } else {
                    chairId = newChairId;
                    isChair = true;
                }
            }
        }
        String sql = "update chair set status=\'x\' where id=?";
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, chairId);
            if (psmt.executeUpdate() == 0) {
                System.out.println("좌석이 이미 예매돼어 있습니다. 다른 좌석을 예매해 주십시오.");
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

