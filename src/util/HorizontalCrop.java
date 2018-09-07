package util;

public class HorizontalCrop implements ICropImage {
    @Override
    public int[] calculateCropSize(int width, int height) {

        //width分为2块，height分为2块，取第0行第0块
        int singleWidth = width /2;
        int singleHeight = height / 2;

        int x = 0;
        int y = 0;

        return new int[]{x, y, singleWidth, singleHeight};
    }
}
