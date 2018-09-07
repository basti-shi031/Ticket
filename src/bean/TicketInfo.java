package bean;

import java.util.Date;

public class TicketInfo {

    String code;//代码
    String number;//号码
    String hashcode;//校验码

    Date transactionDate;//处理日期

    String picPath;//文件路径

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public TicketInfo() {

    }

    public TicketInfo(String code, String number, String hashcode, Date transactionDate, String picPath) {

        this.code = code;
        this.number = number;
        this.hashcode = hashcode;
        this.transactionDate = transactionDate;
        this.picPath = picPath;
    }
}
