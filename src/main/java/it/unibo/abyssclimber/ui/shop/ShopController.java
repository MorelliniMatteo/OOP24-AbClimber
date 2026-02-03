package it.unibo.abyssclimber.ui.shop;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import it.unibo.abyssclimber.model.Item;
import it.unibo.abyssclimber.model.Player;
import it.unibo.abyssclimber.core.SceneRouter;
import it.unibo.abyssclimber.core.GameCatalog;
import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.Refreshable;
import it.unibo.abyssclimber.core.SceneId;

import java.util.List;
import java.util.ArrayList;

/**
 * Controller che gestisce la logica di interazione della schermata del Negozio.
 * Nel pattern MVC, questa classe funge da Controller: intercetta gli eventi
 * dell'interfaccia grafica (definiti nel file FXML), esegue la validazione della logica
 * di acquisto (controllo fondi, disponibilità) e aggiorna lo stato del {@link Player}.
 */
public class ShopController implements ShopControllerInterface, Refreshable {

    /**
     * Riferimenti ai componenti grafici definiti nel file FXML.
     * Grazie all'annotazione {@code @FXML}, il framework JavaFX inietta automaticamente
     * le istanze delle Label corrispondenti agli ID specificati nel file di layout.
     * Queste variabili permettono al codice Java di modificare dinamicamente il testo a video.
     * Sono 4 perché ho 4 slot + 1 label per l'oro del player
     */
    @FXML
    private Label shopSlot1Name, shopSlot1Stats, shopSlot1Price;
    @FXML
    private Label shopSlot2Name, shopSlot2Stats, shopSlot2Price;
    @FXML
    private Label shopSlot3Name, shopSlot3Stats, shopSlot3Price;
    @FXML
    private Label shopSlot4Name, shopSlot4Stats, shopSlot4Price;
    @FXML
    private Label floorLabel;
    @FXML
    private Label hpLabel;
    @FXML
    private Label goldLabel;

    //items che sono in vendita nel negozio, vuoto al momento
    private List<Item> itemsInShop = new ArrayList<>();

    @FXML
    @Override
    public void initialize() {
        // items é uguale agli oggetti in vendita nel negozio presi dal GameCatalog
        List<Item> items = GameCatalog.getShopItems();

        // aggiorniamo la grafica
        updateShopUI(items);

        //mostro l'oro se il player esiste
        refreshHud();
    }

    /**
     * Aggiorna i componenti UI del negozio con i dati degli oggetti passati.
     * Crea una copia locale della lista per gestire lo stato della schermata
     * indipendentemente dalle modifiche esterne durante la visualizzazione.
     * @param shopItems La lista di oggetti da visualizzare.
     */
    @Override
    public void updateShopUI(List<Item> shopItems) {
        this.itemsInShop = new ArrayList<>(shopItems); // copia locale per gestione click, cosí gli indici rimangono corretti anche quando si effettua un acquisto

        //passandoci le variabili dichiarate sopra, succede quello che ho scritto nel primo commento di questo file e le mostra cosí nella grafica, queste variabili diventano le stats di item per il metodo in fondo alla pagina
        updateSingleShopSlot(shopSlot1Name, shopSlot1Stats, shopSlot1Price, getItemSafe(0)); //riempo ogni slot con l'oggetto corrispondente
        updateSingleShopSlot(shopSlot2Name, shopSlot2Stats, shopSlot2Price, getItemSafe(1));
        updateSingleShopSlot(shopSlot3Name, shopSlot3Stats, shopSlot3Price, getItemSafe(2));
        updateSingleShopSlot(shopSlot4Name, shopSlot4Stats, shopSlot4Price, getItemSafe(3));
    }

    @Override
    public void onShow() {
        updateShopUI(GameCatalog.getShopItems());
        refreshHud();
    }

    @FXML
    @Override
    public void onBackClicked() {
        // torna indietro alla selezione stanze
        SceneRouter.goTo(SceneId.SHOP_ROOM);
    }

    // al click del corrispettivo in shop.fxml, @FXML lo collega a questo metodo. La stessa cosa vale per gli altri 3 sotto
    @FXML
    @Override
    public void onSlot1Clicked() {
        tryBuy(0, shopSlot1Name, shopSlot1Price);
    }

    @FXML
    @Override
    public void onSlot2Clicked() {
        tryBuy(1, shopSlot2Name, shopSlot2Price);
    }

    @FXML
    @Override
    public void onSlot3Clicked() {
        tryBuy(2, shopSlot3Name, shopSlot3Price);
    }

    @FXML
    @Override
    public void onSlot4Clicked() {
        tryBuy(3, shopSlot4Name, shopSlot4Price);
    }

    /**
     * Gestisce la logica transazionale di acquisto.
     * Verifica le precondizioni (slot non vuoto, oggetto non venduto, fondi sufficienti).
     * Se l'acquisto va a buon fine, aggiorna il modello (inventario player, rimozione oro)
     * e la vista (segna come "SOLD").
     * @param index L'indice dello slot cliccato.
     * @param nameLbl La label del nome (usata per verificare lo stato visivo).
     * @param priceLbl La label del prezzo.
     */
    private void tryBuy(int index, Label nameLbl, Label priceLbl) {
        Item item = getItemSafe(index); // prende l'item che ha l'indice specificato, l'indice va da 0 a 3

        // se lo slot è vuoto o già venduto, non fare nulla
        if (item == null || nameLbl.getText().equals("SOLD")) {
                return;
        }

        // prende il Player dal GameState
        Player player = GameState.get().getPlayer(); 
        
        // prende il prezzo dell'item
        int prezzo = item.getPrice();
        // prende il gold del player
        int playerGold = player.getGold();

        if(playerGold < prezzo) {
            // non ha abbastanza oro quindi esci
            return;
        }

        // tolgo i soldi
        player.setGold(playerGold - prezzo);
        goldLabel.setText("Gold: " + player.getGold());

        // richiamo il metodo di player per aggiungere l'oggetto all'inventario e applicare le statistiche
        player.addItemToInventory(item); 

        //rimuovo l'oggetto dal negozio (dalla lista presente in GameCatalog), così non può essere ricomprato
        GameCatalog.getShopItems().remove(item);

        // ora appare venduto dove c'era l'oggetto
        nameLbl.setText("SOLD");
        nameLbl.setStyle("-fx-text-fill: gray;");
        priceLbl.setText("");

        // toglie l'oggetto anche dalla lista locale ma lo rendo null cosí gli indici rimangono corretti e non scorrono verso il basso
        itemsInShop.set(index, null); 
    }

    /**
     * Formatta e visualizza i dati di un singolo oggetto nello slot grafico.
     * Utilizza uno {@link StringBuilder} per costruire dinamicamente la stringa delle statistiche,
     * mostrando solo i valori positivi per evitare disordine nell'interfaccia.
     */
    private void updateSingleShopSlot(Label nameLbl, Label statsLbl, Label priceLbl, Item item) { //qui scelgo come mostrare le info dell'oggetto
        if (item == null) {
            nameLbl.setText("---"); // se mancano delle cose negli oggetti allora mette queste cose
            statsLbl.setText("");
            priceLbl.setText("");
            return;
        }
        nameLbl.setText(item.getName().toUpperCase());
        nameLbl.setStyle("-fx-text-fill: white;"); // resetta il colore

        // serve per far apparire bene le stats
        StringBuilder sb = new StringBuilder();
        if (item.getMaxHP() > 0)
            sb.append("MaxHP: +").append(item.getMaxHP()).append("|");
        if (item.getHP() > 0)
            sb.append("HP: +").append(item.getHP()).append("|");
        if (item.getATK() > 0)
            sb.append("ATK: +").append(item.getATK()).append("|");
        if (item.getMATK() > 0)
            sb.append("MATK: +").append(item.getMATK()).append("|");
        if (item.getDEF() > 0)
            sb.append("DEF: +").append(item.getDEF()).append("|");
        if (item.getMDEF() > 0)
            sb.append("MDEF: +").append(item.getMDEF()).append("|");
        if (item.getEffect() != null)
            sb.append("Effect: ").append(item.getEffect()).append("|");
        statsLbl.setText(sb.toString());

        priceLbl.setText(item.getPrice() + " G");
    }

    /**
     * Accesso sicuro alla lista locale degli oggetti con controllo dei limiti (Bound Checking).
     * @param index indice richiesto.
     * @return L'oggetto Item o null se l'indice non è valido.
     */
    private Item getItemSafe(int index) {
        if (index >= 0 && index < itemsInShop.size()) {
            return itemsInShop.get(index); //restituisce l'oggetto con l'indice specificato
        }
        return null;
    }

    private void refreshHud() {
        if (GameState.get().getPlayer() == null) {
            floorLabel.setText("Floor: -");
            hpLabel.setText("HP: -");
            goldLabel.setText("Gold: -");
            return;
        }

        floorLabel.setText("Floor: " + GameState.get().getFloor());
        hpLabel.setText("HP: " + GameState.get().getPlayer().getHP());
        goldLabel.setText("Gold: " + GameState.get().getPlayer().getGold());
    }
}
