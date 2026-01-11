package it.unibo.abyssclimber.ui.shop;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import it.unibo.abyssclimber.model.Item;
import it.unibo.abyssclimber.model.Player;
import it.unibo.abyssclimber.core.SceneRouter;
import it.unibo.abyssclimber.core.GameCatalog;
import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.SceneId;
// import it.unibo.abyssclimber.model.Player;

import java.util.List;
import java.util.ArrayList;

public class ShopController {

    // Riferimenti FXML (già li avevi)
    @FXML
    private Label shopSlot1Name, shopSlot1Stats, shopSlot1Price;
    @FXML
    private Label shopSlot2Name, shopSlot2Stats, shopSlot2Price;
    @FXML
    private Label shopSlot3Name, shopSlot3Stats, shopSlot3Price;
    @FXML
    private Label shopSlot4Name, shopSlot4Stats, shopSlot4Price;

    // MEMORIA: Ci serve per sapere quale oggetto corrisponde a quale slot quando
    // clicchi
    private List<Item> itemsInShop = new ArrayList<>();

    @FXML
    public void initialize() {
        // 1. Chiediamo direttamente al catalogo gli oggetti del negozio
        List<Item> items = GameCatalog.getShopItems();

        // 2. Aggiorniamo la grafica
        updateShopUI(items);
    }

    /**
     * Inizializza il negozio con gli oggetti
     */
    public void updateShopUI(List<Item> shopItems) {
        this.itemsInShop = shopItems; // Salviamo la lista in memoria!

        updateSingleShopSlot(shopSlot1Name, shopSlot1Stats, shopSlot1Price, getItemSafe(0));
        updateSingleShopSlot(shopSlot2Name, shopSlot2Stats, shopSlot2Price, getItemSafe(1));
        updateSingleShopSlot(shopSlot3Name, shopSlot3Stats, shopSlot3Price, getItemSafe(2));
        updateSingleShopSlot(shopSlot4Name, shopSlot4Stats, shopSlot4Price, getItemSafe(3));
    }

    // --- PUNTO 3: Tasto Indietro ---
    @FXML
    public void onBackClicked() {
        // Torna alla mappa o al menu (decidi tu dove)
        SceneRouter.goTo(SceneId.ROOM_SELECTION);
    }

    // --- PUNTO 2: Gestione Click (Acquisto) ---

    @FXML
    public void onSlot1Clicked() {
        tryBuy(0, shopSlot1Name, shopSlot1Price);
    }

    @FXML
    public void onSlot2Clicked() {
        tryBuy(1, shopSlot2Name, shopSlot2Price);
    }

    @FXML
    public void onSlot3Clicked() {
        tryBuy(2, shopSlot3Name, shopSlot3Price);
    }

    @FXML
    public void onSlot4Clicked() {
        tryBuy(3, shopSlot4Name, shopSlot4Price);
    }

    /**
     * Logica di acquisto
     * 
     * @param index    L'indice dell'oggetto nella lista (0-3)
     * @param nameLbl  La label del nome da aggiornare
     * @param priceLbl La label del prezzo da aggiornare
     */
    // In ShopController.java

private void tryBuy(int index, Label nameLbl, Label priceLbl) {
    Item item = getItemSafe(index);

    // Se lo slot è vuoto o già venduto, non fare nulla
    if (item == null || nameLbl.getText().equals("VENDUTO")) {
        return;
    }

    int prezzo = item.getPrice();

    // 1. Recuperiamo il Player dal GameState
    Player player = GameState.get().getPlayer(); 
    
    // Controllo di sicurezza: se per caso il player è null (es. test senza avviare partita)
    if (player == null) {
        System.out.println("Errore: Nessun giocatore trovato!");
        return;
    }

    int playerGold = player.getGold();

    if (playerGold >= prezzo) {
        // --- LOGICA DI ACQUISTO VERA ---
        
        // A. Togli i soldi
        player.setGold(playerGold - prezzo);
        System.out.println("Hai comprato: " + item.getName() + ". Oro rimasto: " + player.getGold());

        // B. Aggiungi all'inventario del giocatore
        // Usiamo il tuo metodo che applica anche le stats subito
        player.addItemToInventory(item); 

        // C. Rimuovi l'oggetto dal GameCatalog (così se esci e rientri non c'è più)
        // Nota: Assicurati di importare GameCatalog
        GameCatalog.getShopItems().remove(item);

        // --- AGGIORNAMENTO GRAFICA ---
        nameLbl.setText("VENDUTO");
        nameLbl.setStyle("-fx-text-fill: gray;");
        priceLbl.setText("");
        
        // Aggiorniamo la lista locale per evitare click doppi
        itemsInShop.set(index, null); 

    } else {
        System.out.println("Non hai abbastanza soldi! (Hai: " + playerGold + ", Serve: " + prezzo + ")");
        // Qui potresti aggiungere un effetto visivo (es. testo rosso temporaneo)
    }
}

    // Helper per evitare crash se la lista è corta
    private void updateSingleShopSlot(Label nameLbl, Label statsLbl, Label priceLbl, Item item) {
        if (item == null) {
            nameLbl.setText("---");
            statsLbl.setText("");
            priceLbl.setText("");
            return;
        }
        nameLbl.setText(item.getName().toUpperCase());
        nameLbl.setStyle("-fx-text-fill: white;"); // Resetta il colore

        // Stats
        StringBuilder sb = new StringBuilder();
        if (item.getATK() > 0)
            sb.append("ATK: +").append(item.getATK()).append("\n");
        if (item.getMATK() > 0)
            sb.append("MATK: +").append(item.getMATK()).append("\n");
        if (item.getDEF() > 0)
            sb.append("DEF: +").append(item.getDEF()).append("\n");
        if (item.getHP() > 0)
            sb.append("HP: +").append(item.getHP()).append("\n");
        statsLbl.setText(sb.toString());

        priceLbl.setText(item.getPrice() + " G");
    }

    private Item getItemSafe(int index) {
        if (index >= 0 && index < itemsInShop.size()) {
            return itemsInShop.get(index);
        }
        return null;
    }
}