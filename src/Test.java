import bean.TicketInfo;
import config.Config;
import manager.TicketInfoManager;
import util.CutImageUtil;
import util.DBUtil;
import util.QRUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws IOException {
//        //存储图片
//        String imgPath = Config.BASE_PATH+"\\img";
//
//        File[] files = new File(imgPath).listFiles();
//        for (File file : files){
//            //裁剪
//            String content = QRUtil.parseQR(file.getAbsolutePath());
//            TicketInfo ticketInfo = TicketInfoManager.parseTicketInfo(content, file.getAbsolutePath());
//            TicketInfoManager.transactTicketInfo(ticketInfo);
//        }

        CutImageUtil.testImg2("D:\\cs\\2.jpg");

    }
}
