// package ants;

// import java.awt.Graphics2D;
// import java.awt.image.BufferedImage;

// /**
//  * LarvaeOnHeadAdder
//  */
// public class LarvaeOnHeadAdder extends ImageOnEventUpdater implements LarvaeSubscriber {

//     public LarvaeOnHeadAdder(LarvaeInfoEmiter ant) {
//         ant.subscribeLarvae(this);
//     }

//     @Override
//     public void onLarvaeAdded() {
//         this.appendLarvaToAllImages();
//     }

//     @Override
//     public void onLarvaeRemoved() {

//     }

//     private void appendLarvaToAllImages() {
//         for (BufferedImage image : this.antGui.getAllImages()) {
//             // thread safe??
//             this.appendLarvaImageOnTop(image);
//         }
//     }

//     private void appendLarvaImageOnTop(BufferedImage image) {
//         BufferedImage larvaImage = this.antGui.loadImage("larva");

//         BufferedImage combined = new BufferedImage(image.getWidth(),
//                 image.getHeight() + larvaImage.getHeight(),
//                 BufferedImage.TYPE_INT_ARGB);

//         // paint both images, preserving the alpha channels
//         Graphics2D g = combined.createGraphics();
//         g.drawImage(image, 0, 0, null);
//         g.drawImage(larvaImage, 0, image.getHeight(), null);

//         // TODO: Add caching

//     }

// }
