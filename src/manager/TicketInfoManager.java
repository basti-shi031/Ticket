package manager;

import bean.TicketInfo;
import util.DBUtil;

import java.sql.SQLException;
import java.util.Date;

public class TicketInfoManager {
    //0   1  2            3       4     5           6                 7    8
    //01,10,012001700112,00147846,48.82,20180612,01874906583038760512,0489,
    //解析数据
    public static TicketInfo parseTicketInfo(String content, String filePath, String qrCodePath) {
        if (content == null || content.length() == 0) {
            return null;
        }
        String[] rawTicket = content.split(",");
        if (rawTicket.length <= 7) {
            return null;
        }

        String code = rawTicket[2];//发票代码
        String number = rawTicket[3];//发票号码
        String hashcode = rawTicket[6];//校验码
        TicketInfo ticketInfo = new TicketInfo(code, number, hashcode, null, filePath);
        ticketInfo.setQrCodePath(qrCodePath);
        return ticketInfo;
    }

    //处理得到的信息
    public static TicketInfo transactTicketInfo(TicketInfo ticketInfo) {
        //流程：
        //1、根据解析的得到的票据信息，查数据库中是否存在
        //2.1如果不存在，则入库
        //2.2如果存在，将两张图片都展示出来
        DBUtil.init();
        TicketInfo originTicketInfo = null;
        try {
            originTicketInfo = DBUtil.isExist(ticketInfo);
            if (originTicketInfo == null) {
                //没有找到，入库
                DBUtil.save(ticketInfo);
            } else {
                //找到，说明已经有了，返回原有数据
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close();
            return originTicketInfo;
        }

    }

}
