package service;
import repository.Repository;
public class UserService {
    static Repository rp=new Repository();
    public static void printPreview() {
        //printPreview
        rp.printPreview();
    }
    public static void printCinema() {
        //printPreview
        rp.printCinema();
    }

    public static void relatedCinemaprint(String previewId) {
        rp.relatedCinemaPrint(previewId);
    }

    public static void relatedPreviewPrint(String cinemaId){
        rp.relatedPreviewPrint(cinemaId);
    }
    public static void printChair(String cinemaId) {
        rp.printChair(cinemaId);
    }

    public static void changeStatusAndPersonInsert(String chairId,String name,String phoneNum) {
        rp.changeStatusAndPersonInsert(chairId,name,phoneNum);
    }

    public static int getCinema() {


        return 1;
    }
    public static int getPreview(int cinemaId) {
        return 1;
    }


}
