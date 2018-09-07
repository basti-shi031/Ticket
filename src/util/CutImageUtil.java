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

    /**
     * 本地获取图片大小
     */
    public static int[] testImg2(String imgPath) throws IOException {
        File picture = new File(imgPath);
        BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
        System.out.println(sourceImg.getWidth()); // 源图宽度
        System.out.println(sourceImg.getHeight()); // 源图高度
        return new int[]{sourceImg.getWidth(), sourceImg.getHeight()};
    }

    /**
     *
     * @Description: 剪切本地图片
     *  @param imagePath
     * String
     */
/*    public static String cutLocalImage(String imagePath) throws IOException {
            // 首先通过ImageIo中的方法，创建一个Image + InputStream到内存
            ImageInputStream iis = ImageIO
                    .createImageInputStream(new FileInputStream(imagePath));
            // 再按照指定格式构造一个Reader（Reader不能new的）
            Iterator it = ImageIO.getImageReadersByFormatName("png");
            ImageReader imagereader = (ImageReader) it.next();
            // 再通过ImageReader绑定 InputStream
            imagereader.setInput(iis);

            // 设置感兴趣的源区域。
            ImageReadParam par = imagereader.getDefaultReadParam();
            par.setSourceRegion(new Rectangle(x, y, w, h));
            // 从 reader得到BufferImage
            BufferedImage bi = imagereader.read(0, par);

            // 将BuffeerImage写出通过ImageIO

            ImageIO.write(bi, "png", new File(filePath));
        }*/

}