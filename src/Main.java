import bean.TicketInfo;
import config.Config;
import manager.TicketInfoManager;
import org.apache.commons.io.FileUtils;
import util.BitmapUtil;
import util.CutImageUtil;
import util.QRUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    static int index = 0;
    static File[] ticketImages;

    public static void main(String[] args) {
        // 1. 创建一个顶层容器（窗口）
        JFrame jf = new JFrame("发票");          // 创建窗口
        jf.setSize(1100, 800);                       // 设置窗口大小
        jf.setLayout(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）

        // 2. 创建中间容器（面板容器）
//        JPanel panel = new JPanel();                // 创建面板容器，使用默认的布局管理器
//        panel.setLayout(null);
//        panel.setBackground(Color.BLUE);

        JLabel ticketImageView = new JLabel();
        ticketImageView.setBounds(50, 50, 1000, 500);
        ticketImageView.setBackground(Color.green);
        jf.add(ticketImageView);

        JLabel hintTv = new JLabel("请输入报账日期：（20180101）");
        hintTv.setBounds(50, 600, 200, 30);
        jf.add(hintTv);

        JTextField dateEditText = new JTextField();
        dateEditText.setBounds(300, 600, 200, 30);
        jf.add(dateEditText);

        // 3. 创建一个基本组件（按钮），并添加到 面板容器 中
        JButton confirmBt = new JButton("确定");
        confirmBt.setBounds(600, 600, 100, 30);
        jf.add(confirmBt);
        confirmBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = dateEditText.getText();
                try {
                    Date transactionDate = dateFormat.parse(date);

                    //对于每张图片，
                    //1.切割二维码
                    //2.扫描二维码
                    //3.根据扫描结果访问数据库

                    //截取二维码并获得二维码路径
                    String qrCodePath = null;
                    File ticketImg = ticketImages[index];
                    try {
                        qrCodePath = CutImageUtil.cutLocalImage(ticketImg.getAbsolutePath(), ticketImg.getName(), Config.QRCODE_PATH);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    //根据二维码解析二维码
                    String content = QRUtil.parseQR(qrCodePath);
                    //根据解析完的信息，得到ticketInfo实例
                    TicketInfo ticketInfo = TicketInfoManager.parseTicketInfo(content, ticketImg.getAbsolutePath(), qrCodePath);
                    ticketInfo.setTransactionDate(transactionDate);

                    //得到该实例后，根据其code 和 number 访问数据库，如果数据库中不存在，则入库，否则返回库内原有数据
                    TicketInfo originTicketInfo = TicketInfoManager.transactTicketInfo(ticketInfo);
                    if (originTicketInfo == null) {
                        //origin为空说明新入库，将原图移入used文件夹
                        File newFile = new File(Config.USED_PATH + "\\" + ticketImg.getName());
                        try {
                            FileUtils.moveFile(ticketImg, newFile);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        //origin不为空，说明已经存在
                        //移入trash文件夹
                        File newFile = new File(Config.TRASH_PATH + "\\" + ticketImg.getName());
                        try {
                            FileUtils.moveFile(ticketImg, newFile);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        String transactionDateStr = dateFormat.format(originTicketInfo.getTransactionDate());
                        JOptionPane.showMessageDialog(null, "发票代码：" + ticketInfo.getCode() + ",发票号码：" + ticketInfo.getNumber() + "已经在" + transactionDateStr + "报账", "重复报账", JOptionPane.ERROR_MESSAGE);
                    }

                    index++;
                    if (index >= ticketImages.length) {
                        JOptionPane.showMessageDialog(null, "完成", "完成", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    } else {
                        ImageIcon icon = new ImageIcon(ticketImages[index].getAbsolutePath());
                        icon = new ImageIcon(icon.getImage().getScaledInstance(1000, 500, Image.SCALE_DEFAULT));
                        ticketImageView.setIcon(icon);
                    }

                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // 4. 把 面板容器 作为窗口的内容面板 设置到 窗口
        // jf.setContentPane(panel);

        // 5. 显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
        jf.setVisible(true);


        //处理业务逻辑
        //初始化各个文件夹
        init();
        //旋转图片
        rotate();
        //遍历下ticket目录下所有文件
        ticketImages = new File(Config.TICKET_PATH).listFiles();
        if (ticketImages.length == 0) {
            JOptionPane.showMessageDialog(null, Config.TICKET_PATH + "目录下没有发票图片", "无发票信息", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        ImageIcon icon = new ImageIcon(ticketImages[0].getAbsolutePath());
        icon = new ImageIcon(icon.getImage().getScaledInstance(1000, 500, Image.SCALE_DEFAULT));
        ticketImageView.setIcon(icon);
    }

    private static void rotate() {
        File[] rawFiles = new File(Config.RAW_PATH).listFiles();
        for (File rawFile : rawFiles) {
            try {
                BitmapUtil.spin(270, rawFile.getAbsolutePath(), rawFile.getName(), Config.TICKET_PATH);
                rawFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void init() {
        createDir(Config.TICKET_PATH);
        createDir(Config.QRCODE_PATH);
        createDir(Config.USED_PATH);
        createDir(Config.TRASH_PATH);
    }

    private static void createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 旋转图片为指定角度
     *
     * @param bufferedimage 目标图像
     * @param degree        旋转角度
     * @return
     */
    public static BufferedImage rotateImage(final BufferedImage bufferedimage,
                                            final int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type)).createGraphics())
                .setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }
}
