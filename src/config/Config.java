package config;

public class Config {
    //C:\cs\javaspace\Ticket
    public static String BASE_PATH = System.getProperty("user.dir");
    public static String TICKET_PATH = BASE_PATH + "\\ticket";//旋转后的原图
    public static String QRCODE_PATH = BASE_PATH + "\\qrcode";//截下来的二维码
    public static String USED_PATH = BASE_PATH + "\\used";//已经使用了的
    public static String TRASH_PATH = BASE_PATH + "\\trash";//已经报账过的
    public static String RAW_PATH = BASE_PATH + "\\raw";//原图
}
