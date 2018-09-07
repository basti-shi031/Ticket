package util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class QRUtil {
    public static String parseQR(String filePath){
        try {
            MultiFormatReader reader=new MultiFormatReader();//需要详细了解MultiFormatReader的小伙伴可以点我一下官方去看文档
            File f=new File(filePath);
            BufferedImage image=ImageIO.read(f);
            BinaryBitmap bb=new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
            HashMap map =new HashMap();
            map.put(EncodeHintType.CHARACTER_SET, "utf-8");
            Result result = reader.decode(bb,map);
//            System.out.println("解析结果："+result.toString());
//            System.out.println("二维码格式类型："+result.getBarcodeFormat());
//            System.out.println("二维码文本内容："+result.getText());
            return result.getText();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
