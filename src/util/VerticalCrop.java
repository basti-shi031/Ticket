package util;

/**
 * 竖直分割
 */
public class VerticalCrop implements ICropImage {
    @Override
    public int[] calculateCropSize(int width, int height) {
        //width分割成2块，height分割成2块，取第0行最后一块
        int singleWidth = width / 2;
        int singleHeight = height / 2;

        int x = singleWidth * 1;
        int y = 0;

        return new int[]{x, y, singleWidth, singleHeight};
    }
}
