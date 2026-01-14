package it.unibo.abyssclimber.ui.combat;

import java.util.List;

import it.unibo.abyssclimber.core.GameCatalog;
import it.unibo.abyssclimber.core.GameState;
import it.unibo.abyssclimber.core.combat.BattleText;
import it.unibo.abyssclimber.core.combat.Combat;
import it.unibo.abyssclimber.core.combat.CombatLog;
import it.unibo.abyssclimber.core.combat.LogType;
import it.unibo.abyssclimber.core.combat.MoveLoader.Move;
import it.unibo.abyssclimber.model.Creature;
import it.unibo.abyssclimber.model.Player;
import it.unibo.abyssclimber.model.Tipo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class CombatController {
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

    private Player player;
    private Creature monster;
    private Combat combat;
    private List<Button> buttonList;
    private CombatLog combatLog;
    private boolean combatEnded = false;
    
    //TODO: fix
    
    public CombatController() {
        this.player = GameState.get().getPlayer();
        this.monster = GameCatalog.getRandomMonsterByStage(GameState.get().getFloor());
    }

    public CombatController(boolean b) {
        this.player = GameState.get().getPlayer();
        this.monster = GameCatalog.getRandomMonsterByStage(GameState.get().getFloor());
        setElite(b);
    }
    
    @FXML
    private void initialize() {
        this.combatLog = new CombatLog();
        this.combat = new Combat(player, monster, combatLog, this);
        buttonList = List.of(move1Button, move2Button, move3Button, move4Button, move5Button, move6Button);
        setMoveButton(player);
        applyBackground(topPane, monster);
        player.setSTAM(player.regSTAM());
        labelHP.setText("HP: " + player.getHP() + "/" + player.getMaxHP());
        labelMP.setText("MP: " + player.getSTAM() + "/" + player.getMaxSTAM() + " +" + player.regSTAM());
        combatLog.logCombat("Room entered. Enemy is a " + monster.getName() + ".", LogType.NORMAL);
        this.renderLog();
        enableMoveButtons();
        //TODO: remove
        System.out.println(player.getSTAM());
        monster.setMaxHP(500);
        monster.setHP(500);
    }
    
    public void setElite(boolean b) {
        if (b) {
            monster.setIsElite(b);
            monster.promoteToElite();
        }
    }

    private void applyBackground(Pane topPane, Creature monster) {
        if ( monster.getIsElite()) {
            topPane.getStyleClass().add("combat-bg-elite");
            System.out.println("elite BG.");
        } else if ( monster.getStage().equalsIgnoreCase("BOSS")) {
            topPane.getStyleClass().add("combat-bg-boss");
            System.out.println("boss bg.");
        } else {
            topPane.getStyleClass().add("combat-bg-normal");
            System.out.println("normal bg");
        }
    }

    private void applyTipoStyle(Button b, Tipo tipo){
        b.getStyleClass().removeIf(s -> s.startsWith("tipo-"));
        b.getStyleClass().add("tipo-" + tipo.name().toLowerCase());
    }

    private void setMoveButton (Player player){
        for ( int i = 0; i < player.getSelectedMoves().size(); i++ ) {
            Button b = buttonList.get(i);
            Move mv = player.getSelectedMoves().get(i);
            b.setText(mv.getName() + "\n" + "Potenza " + mv.getPower() + " | Acc " + mv.getAcc() + " | Costo " +mv.getCost());
            b.setUserData(mv);
            applyTipoStyle(b, mv.getElement());
        }
    }

    @FXML
    private void onMovePressed(ActionEvent e){
        Button clicked = (Button) e.getSource();
        Move move = (Move) clicked.getUserData();
        disableMoveButtons();
        combat.fight(move);
        enableMoveButtons();
    }

    public void disableMoveButtons() {
        buttonList.forEach(b -> b.setDisable(true));
    }

    public void enableMoveButtons() {
        if(combatEnded) return;
        buttonList.forEach(b -> b.setDisable(false));
    }

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

    public void updateStats() {
        labelHP.setText("HP: " + player.getHP() + "/" + player.getMaxHP());
        labelMP.setText("MP: " + player.getSTAM() + "/" + player.getMaxSTAM() + " +" + player.regSTAM());
    }

    public void setCombatEnd(boolean b) {
        combatEnded = b;
        if (b) {
            disableMoveButtons();
        }
    }
}
