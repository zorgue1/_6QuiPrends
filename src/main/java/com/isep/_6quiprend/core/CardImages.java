package com.isep._6quiprend.core;

import javafx.scene.image.Image;

import java.net.URL;

public class CardImages {

    private static final Image backsideImage = createBacksizeImage();

    private static final Image[] cardImages = createCardImages();

    //---------------------------------------------------------------------------------------------

    public static Image getBacksideImage() {
        return backsideImage;
    }

    public static Image getFrontCardImage(Card card) {
        return (card != null)? cardImages[card.getNumber()] : backsideImage;
    }

    private static Image createBacksizeImage() {
        URL imgUrl = CardImages.class.getResource("/com/example/_6quiprend/image/backside.png");
        return new Image(imgUrl.toExternalForm());
    }

    private static Image[] createCardImages() {
        Image[] images = new Image[105];
        images[0] = null;
        for(int i = 1; i <= 104; i++) {
            URL imgUrl = CardImages.class.getResource("/com/example/_6quiprend/image/" + i + ".png");
            images[i] = new Image(imgUrl.toExternalForm());
        }
        return images;
    }
}
