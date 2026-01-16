package it.unibo.abyssclimber.ui.shop;

import it.unibo.abyssclimber.model.Item;
import java.util.List;

public interface ShopControllerInterface {
    void initialize();

    void updateShopUI(List<Item> shopItems);

    void onBackClicked();

    void onSlot1Clicked();

    void onSlot2Clicked();

    void onSlot3Clicked();

    void onSlot4Clicked();
}