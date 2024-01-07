//package ants;

//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;

///**
// * LarveableAntGui
// */
//public class LarveableAntGui extends AntGui {
//    int larvaeDrawn = 0;
//    CarryLarvae larvaeCarrer;

//    public LarveableAntGui(Ant ant, CarryLarvae larvaeCarrer, AntImageConfig imageConfig) {
//        super(ant, imageConfig);
//        this.larvaeCarrer = larvaeCarrer;
//    }

//    private BufferedImage appendLarvaImageOnTop(BufferedImage image) {
//        BufferedImage larvaImage = this.loadImage("larva");

//        BufferedImage combined = new BufferedImage(image.getWidth(),
//                image.getHeight() + larvaImage.getHeight(),
//                BufferedImage.TYPE_INT_ARGB);

//        // paint both images, preserving the alpha channels
//        Graphics2D g = combined.createGraphics();
//        g.drawImage(image, 0, 0, null);
//        g.drawImage(larvaImage, 0, image.getHeight(), null);

//        return combined;

//    }

//    // TODO: Add caching
//    //
//    @Override
//    public void update(int timeElapsed) {
//        super.update(timeElapsed);
//        if (this.larvaeCarrer.getNumberOfLarvaes().get() > this.larvaeDrawn) {
//            BufferedImage image = this.getDisplayedImage();

//            while (this.larvaeCarrer.getNumberOfLarvaes().get() > this.larvaeDrawn) {
//                image = this.appendLarvaImageOnTop(image);
//                this.larvaeDrawn++;
//            }
//            this.setDisplayedImage(image);
//        }

//        // if (this.larvaeCarrer.getNumberOfLarvaes().get() < this.larvaeDrawn) {
//        //     BufferedImage image = this.getDisplayedImage();

//        //     while (this.larvaeCarrer.getNumberOfLarvaes().get() < this.larvaeDrawn) {
//        //         image = this.removeLarvaImageFromTop(image);
//        //         this.larvaeDrawn--;
//        //     }
//        //     this.setDisplayedImage(image);
//        // }
//    }
//}
