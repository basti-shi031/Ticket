import bean.TicketInfo;
import config.Config;
import manager.TicketInfoManager;
import util.CutImageUtil;
import util.QRUtil;

import java.io.File;
import java.io.IOException;


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

        //初始化各个文件夹
        init();

        //遍历下ticket目录下所有文件
        File[] ticketImages = new File(Config.TICKET_PATH).listFiles();
        for (File ticketImg : ticketImages) {
            //对于每张图片，
            //1.切割二维码
            //2.扫描二维码
            //3.根据扫描结果访问数据库

            //截取二维码并获得二维码路径
            String qrCodePath = CutImageUtil.cutLocalImage(ticketImg.getAbsolutePath(), ticketImg.getName(), Config.QRCODE_PATH);
            //根据二维码解析二维码
            String content = QRUtil.parseQR(qrCodePath);
            //根据解析完的信息，得到ticketInfo实例
            TicketInfo ticketInfo = TicketInfoManager.parseTicketInfo(content, ticketImg.getAbsolutePath(), qrCodePath);
            //得到该实例后，根据其code 和 number 访问数据库，如果数据库中不存在，则入库，否则返回库内原有数据
            TicketInfo originTicketInfo = TicketInfoManager.transactTicketInfo(ticketInfo);
            if (originTicketInfo == null) {
                //origin为空说明新入库，没有操作
            } else {
                //origin不为空，说明已经存在
            }
        }
    }

    private static void init() {
        createDir(Config.TICKET_PATH);
        createDir(Config.QRCODE_PATH);
    }

    private static void createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
