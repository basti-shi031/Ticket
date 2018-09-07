package util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * 剪裁图片(从网络上获取图片) 21  * @description
 *
 * @time 2013-2-24下午02:07:00 24  * @version
 */
public class CutImageUtil {

    private static int VERTICAL = 0;
    private static int HORIZONTAL = 1;

    static ICropImage cropImageImpl;

    /**
     * 本地获取图片大小
     */
    private static int[] getImageSize(String imgPath) throws IOException {
        File picture = new File(imgPath);
        FileInputStream fileInputStream = new FileInputStream(picture);
        BufferedImage sourceImg = ImageIO.read(fileInputStream);
        fileInputStream.close();
        return new int[]{sourceImg.getWidth(), sourceImg.getHeight()};
    }

    /**
     * @Description: 剪切本地图片
     */
    public static String cutLocalImage(String originPath, String fileName, String qrCodeDir) throws IOException {
        // 首先通过ImageIo中的方法，创建一个Image + InputStream到内存
        int[] sizes = getImageSize(originPath);
        int width = sizes[0];
        int height = sizes[1];

        //判别照片是横向还是竖向
        //如果宽大于高为横向，反之为竖向
        int orientation = width > height ? HORIZONTAL : VERTICAL;
        if (orientation == HORIZONTAL) {
            //横向切割
            cropImageImpl = new HorizontalCrop();
        } else {
            cropImageImpl = new VerticalCrop();
        }
        int[] rectParam = cropImageImpl.calculateCropSize(width, height);
        FileInputStream fileInputStream = new FileInputStream(originPath);
        ImageInputStream iis = ImageIO
                .createImageInputStream(fileInputStream);
        // 再按照指定格式构造一个Reader（Reader不能new的）
        Iterator it = ImageIO.getImageReadersByFormatName(fileName.split("\\.")[1]);
        ImageReader imagereader = (ImageReader) it.next();
        // 再通过ImageReader绑定 InputStream
        imagereader.setInput(iis);

        // 设置感兴趣的源区域。
        ImageReadParam par = imagereader.getDefaultReadParam();
        par.setSourceRegion(new Rectangle(rectParam[0], rectParam[1], rectParam[2], rectParam[3]));
        // 从 reader得到BufferImage
        BufferedImage bi = imagereader.read(0, par);

        // 将BuffeerImage写出通过ImageIO
        String qrCodePath = qrCodeDir + "\\" + fileName;
        ImageIO.write(bi, fileName.split("\\.")[1], new File(qrCodePath));
        fileInputStream.close();
        iis.close();
        return qrCodePath;
    }

}