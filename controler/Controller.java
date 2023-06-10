package controler;
import repository.Repository;
import service.AdminService;
import java.util.Scanner;
import service.UserService;
//입력받은거 분배하는 컨트롤러
public class Controller {
    Scanner sc=new Scanner(System.in);
    UserService us=new UserService();
    AdminService as=new AdminService();
    public  void selectMode(){
        System.out.print("1:관리자 모드로 접속\n2:관객 모드로 접속\n입력 : ");
        String mode=sc.nextLine();
        if (mode.equals("1")) {
            System.out.println("관리자 모드로 접속했습니다!");
            while(true){
                System.out.print("1:시사회 정보 등록하기\n2:영화관 정보 등록하기\n3:시사회 일정과 영화관 연결하기\n4:예약자 명단 확인하기\n5:모드 선택 화면 돌아가기\n입력 : ");
                String adminMode=sc.nextLine();
                switch (adminMode){
                    case "1":
                        //todo preview_insert
                        System.out.print("영화이름 : ");
                        String movieName=sc.nextLine();
                        System.out.print("상영날짜 : ");
                        String dateOfPreview=sc.nextLine();
                        while (true) {
                            if (as.previewInsert(movieName, dateOfPreview)) {
                                System.out.println("시사회 정보가 성공적으로 등록되었습니다!");
                                break; // 입력 성공 종료
                            } else {
                                System.out.println("중복된 시사회 정보입니다... 확인 후 다시 입력해주세요!");
                                System.out.print("영화이름 : ");
                                movieName=sc.nextLine();
                                System.out.print("상영날짜 : ");
                                dateOfPreview=sc.nextLine();
                            }
                        }
                        break;
                    case "2":
                        //todo cinema_insert
                        System.out.print("영화관 이름 : ");
                        String cinemaName=sc.nextLine();
                        System.out.print("지점 : ");
                        String address=sc.nextLine();
                        System.out.print("총좌석수 : ");
                        String numChair=sc.nextLine();
                        while (true) {
                            if (as.cinemaInsert(cinemaName, address, numChair)) {
                                System.out.println("영화관 정보가 성공적으로 등록되었습니다!");
                                break; // 입력 성공 종료
                            } else {
                                System.out.println("중복된 영화관입니다... 영화관을 확인 후 다시 입력해주세요!");
                                System.out.print("영화관 이름을 다시 입력하세요 : ");
                                cinemaName = sc.nextLine();
                                System.out.print("지점  : ");
                                address=sc.nextLine();
                                System.out.print("총좌석수 : ");
                                numChair=sc.nextLine();
                            }
                        }
                        break;
                    case "3":
                        //todo combine_cinema_preview
                        System.out.println("시사회 정보입니다!");
                        as.printPreview();
                        System.out.print("시사회 일정을 선택하세요! -> 시사회No. : ");
                        String preiviewId=sc.nextLine();
                        System.out.println("영화관 정보입니다!");
                        as.printCinema();
                        System.out.print("상영 영화관을 선택하세요! -> 영화관No. : ");
                        String cinemaId=sc.nextLine();
                        as.combineCinemaPreview(preiviewId,cinemaId);
                        break;
                    case "4":
                        //todo get_person
                        System.out.println("예약자 명단입니다!");
                        as.printPerson();
                        break;
                    case"5":
                        selectMode();
                }
            }
        }
        if(mode.equals("2")){
            System.out.println("유저모드로 접속했습니다!");
            System.out.print("1:시사회 정보 확인\n2:영화관내 시사회 정보 확인\n 입력 : ");
            String firstMode=sc.nextLine();
            if (firstMode.equals("1")){
                System.out.println("시사회 정보를 확인 후 선택해주세요!");
                us.printPreview();//시사회 정보 출력
                System.out.print("시사회No. : ");
                String previewId=sc.nextLine();
                System.out.println("해당 날짜에 상영하는 영화관 정보입니다!");
                us.relatedCinemaprint(previewId);//시네마 상영하는 영화관 출력
                System.out.print("예약을 하려면 영화관No.를 선택해주세요 -> 영화관No. = ");
                String cinemaId=sc.nextLine();
                System.out.println("남은 좌석 정보를 확인해주세요!");
                us.printChair(cinemaId);
                System.out.print("예약 좌석을 선택해주세요! -> 좌석No. : ");
                String chairId=sc.nextLine();
                System.out.print("예약 고객님 성함을 입력해주세요! -> 이름 : ");
                String name=sc.nextLine();
                System.out.print("예약 고객님 핸드폰 번호를 입력해주세요! -> 핸드폰 번호 : ");
                String phoneNum=sc.nextLine();
                us.changeStatusAndPersonInsert(chairId,name,phoneNum);//좌석 선택
                selectMode();
            }
            else if (firstMode.equals("2")){
                int cinemaId=us.getCinema();//영화관 정보 출력하고, 선택한 시사회 아이디 반환
                int previewId=us.getPreview(cinemaId);

                selectMode();
            }
        }

    }

}
