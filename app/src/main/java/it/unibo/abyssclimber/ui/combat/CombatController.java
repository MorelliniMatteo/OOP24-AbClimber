package it.unibo.abyssclimber.ui.combat;

import java.util.List;

import it.unibo.abyssclimber.core.GameCatalog;
import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.SceneId;
import it.unibo.abyssclimber.core.SceneRouter;
import it.unibo.abyssclimber.core.combat.BattleText;
import it.unibo.abyssclimber.core.combat.Combat;
import it.unibo.abyssclimber.core.combat.CombatLog;
import it.unibo.abyssclimber.core.combat.CombatMove;
import it.unibo.abyssclimber.core.combat.CombatPresenter;
import it.unibo.abyssclimber.core.combat.LogType;
import it.unibo.abyssclimber.core.combat.MoveLoader.Move;
import it.unibo.abyssclimber.model.Creature;
import it.unibo.abyssclimber.model.Difficulty;
import it.unibo.abyssclimber.model.Player;
import it.unibo.abyssclimber.model.Tipo;
import it.unibo.abyssclimber.ui.assets.CreaturesAssets;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

/**
 * Class responsible for handling the combat GUI cycle.
 */
public class CombatController  implements CombatPresenter{
    @FXML private Button move1Button;
    @FXML private Button move2Button;
    @FXML private Button move3Button;
    @FXML private Button move4Button;
    @FXML private Button move5Button;
    @FXML private Button move6Button;
    @FXML private TextFlow logFlow;
    @FXML private Pane topPane;
    @FXML private Label labelHP;
    @FXML private Label labelMP;
    @FXML private ImageView monsterImage;
    @FXML private StackPane monsterContainer;
    @FXML private VBox drawer;
    @FXML private Label drawerATK;
    @FXML private Label drawerMATK;
    @FXML private Label drawerDEF;
    @FXML private Label drawerMDEF;
    @FXML private Label drawerCR;
    @FXML private Label drawerCDM;

    private Player player;
    private Creature monster;
    private Combat combat;
    private List<Button> buttonList;
    private CombatLog combatLog;
    private boolean combatEnded = false;
    private boolean drawerOpen = false;

    //Constructor without parameters for FXML. Is called for all fights against enemies that are not "elite".
    public CombatController() {
        this.player = GameState.get().getPlayer();
        this.monster = GameCatalog.getRandomMonsterByStage(Math.min(9, GameState.get().getFloor()));
        this.monster.applyDifficultyMultiplier(Difficulty.getDifficultyMultiplier());
    }

    //Constructor used when enemies are elite. Called by factory ovveride. 
    public CombatController(boolean b) {
        this.player = GameState.get().getPlayer();
        this.monster = GameCatalog.getRandomMonsterByStage(GameState.get().getFloor());
        this.monster.applyDifficultyMultiplier(Difficulty.getDifficultyMultiplier());
        setElite(b);
    }
    
    //Standard FXML initialization. 
    @FXML
    private void initialize() {
        this.combatLog = new CombatLog();
        this.combat = new Combat(player, monster, combatLog, this);
        buttonList = List.of(move1Button, move2Button, move3Button, move4Button, move5Button, move6Button);
        setMoveButton(player);
        applyBackground(monsterContainer, monster);
        System.out.println("ID: " + monster.getId());
        System.out.println("Monster: " + monster.getName());
        loadMonsterImage();
        drawerSet();
        player.setSTAM(player.getRegSTAM());
        updateStats();
        combatLog.logCombat("Room entered. Enemy is a " + monster.getName() + ".\n", LogType.NORMAL);
        this.renderLog();
        enableMoveButtons();
        System.out.println(player.getSTAM());
    }
    
    //If enemy is flagged as an elite calls the promotion methods and sets it's flag.
    public void setElite(boolean b) {
        if (b) {
            monster.promoteToElite();
        }
    }

    //Applies the stage background: 1 of 3 variations depending on the enemy type.
    private void applyBackground(Pane bgPane, Creature monster) {
        if ( monster.getIsElite() && !monster.getStage().equalsIgnoreCase("BOSS")) {
            bgPane.getStyleClass().addAll("combat-bg-elite", "combat-bg");
        } else if ( monster.getStage().equalsIgnoreCase("BOSS")) {
            bgPane.getStyleClass().addAll("combat-bg-boss", "combat-bg");
        } else {
            bgPane.getStyleClass().addAll("combat-bg-normal", "combat-bg");
        }
    }

    //Loads the appropriate monster image using enemy ID.
    private void loadMonsterImage() {
        var image = CreaturesAssets.getMonsterImage(monster.getId());
        monsterImage.setImage(image); 
    }

    //Changes the move buttons color to match their element.
    /**
     * Changes the move buttons color to match their element.
     * Styles are stored in combat.css 
     * @param b the button to color
     * @param tipo the {@link Tipo} of the {@link CombatMove} assigned to the button
     */
    private void applyTipoStyle(Button b, Tipo tipo){
        b.getStyleClass().removeIf(s -> s.startsWith("tipo-"));
        b.getStyleClass().add("tipo-" + tipo.name().toLowerCase());
    }

    /**
     * Writes the values of the {@link CombatMove} values on the move button, sets their user data and applies a background colour.
     * @param player the active {@link Player}, necessary to obtain the selected {@link CombatMove} on character creation.
     */
    private void setMoveButton (Player player){
        for ( int i = 0; i < player.getSelectedMoves().size(); i++ ) {
            Button b = buttonList.get(i);
            Move mv = player.getSelectedMoves().get(i);
            b.setText(mv.getName() + "\n" + "Power " + mv.getPower() + " | Accuracy " + mv.getAcc() + " | Cost " +mv.getCost());
            b.setUserData(mv);
            applyTipoStyle(b, mv.getElement());
        }
    }

    //Actions to perform on a move button click by the player.
    @FXML
    private void onMovePressed(ActionEvent e){
        Button clicked = (Button) e.getSource();
        CombatMove move = (CombatMove) clicked.getUserData();
        disableMoveButtons();
        combat.fight(move);
        enableMoveButtons();
    }

    //Called to disable the move buttons during enemy turn (very short) to prevent multiple clicks and post victory (noticeable time).
    public void disableMoveButtons() {
        buttonList.forEach(b -> b.setDisable(true));
    }

    //Enables move buttons.
    public void enableMoveButtons() {
        if(combatEnded) return;
        buttonList.forEach(b -> b.setDisable(false));
    }

    //Method to render all the logs in queue with their appropriate coloring.
    public void renderLog() {
        logFlow.getChildren().clear();

        for (var line : combatLog.getEvents()) {
            for ( BattleText bt : line) {
                Text t = new Text(bt.text());

                switch (bt.type()) {
                    case NORMAL -> t.setFill(Color.WHITE);
                    case DAMAGE -> t.setFill(Color.RED);
                    case CRITICAL -> t.setFill(Color.GOLD);
                }
                logFlow.getChildren().add(t);

            }
        }
    }

    //Method to update the player HP and MP in the combat screen. 
    //Other stats cannot change during a battle, and as such do not need to be updated.
    @Override
    public void updateStats() {
        labelHP.setText("HP: " + player.getHP() + "/" + player.getMaxHP());
        labelMP.setText("MP: " + player.getSTAM() + "/" + player.getMaxSTAM() + " +" + player.getRegSTAM());
    }

    //Flag on combat end.
    @Override
    public void setCombatEnd(boolean b) {
        combatEnded = b;
        if (b) {
            disableMoveButtons();
        }
    }

    //Method to open the player stat page on the battle screen.
    //Implemented as a VBox containing labels for each stat that appears from the left.
    @FXML
    public void toggleDrawer() {
        double targetWidth = drawerOpen ? 0 : 150;
        Timeline timeline = new Timeline( new KeyFrame(Duration.millis(250), new KeyValue(drawer.prefWidthProperty(), targetWidth) ) ); timeline.play();
        drawerOpen = !drawerOpen;
    }

    @Override
    public void onTurnStart(int turn) {
        combatLog.clearEvents();
        combatLog.logCombat("Turn " + turn + "\n", LogType.NORMAL);
        renderLog();
    }

    @Override
    public void onCombatEnd(boolean finalBoss, boolean elite, boolean playerWon) {
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(e -> {
                if (playerWon) {

                    if (finalBoss) {
                        SceneRouter.goTo(SceneId.WIN);
                        return;
                    }
                    if (monster.getIsElite()) {
                        GameState.get().nextFloor();
                    }
                    SceneRouter.goTo(SceneId.ROOM_SELECTION);

                } else {
                    SceneRouter.goTo(SceneId.GAME_OVER);
                }
            });
        pause.play();
    }

    private void drawerSet() {
        drawerATK.setText("ATK: " + player.getATK());
        drawerMATK.setText("MATK: " + player.getMATK());
        drawerDEF.setText("DEF: " + player.getDEF());
        drawerMDEF.setText("MDEF: " + player.getMDEF());
        drawerCR.setText("Crit \nRate: " + player.getCrit() + "%");
        drawerCDM.setText("Crit \nDamage: " + (int)(player.getCritDMG()*100) + "%");
    }
}
