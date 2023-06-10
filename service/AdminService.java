package service;

import repository.Repository;
import Connection.JdbcConnection;
import java.sql.Connection;

import static repository.Repository.*;

public class AdminService {
    static Repository rp = new Repository();
    static Connection conn = new JdbcConnection().getJdbc();


    public static boolean previewInsert(String movieName, String dateOfPreview) {
        if (isDuplicatePreview(conn,movieName, dateOfPreview)) { //시사회 날짜로 중복방지
            return false;
        }
        rp.preInsert(movieName, dateOfPreview);
        return true;
    }

    public static boolean cinemaInsert(String cinemaName, String address, String numChair) {
        if (isDuplicateCinemaName(conn, cinemaName, address)) { //영화관이름,주소로 중복방지
            return false;
        }
        rp.cinInsert(cinemaName, address, numChair);
        return true;
    }

        public static void printCinema () {
            rp.printCinema();
        }

        public static void printPreview () {
            rp.printPreview();
        }
        public static void combineCinemaPreview (String previewId, String cinemaId){
            rp.insertCombinePreview(previewId, cinemaId);
        }
        public static void printPerson () {
            rp.printPerson();
        }


    }

