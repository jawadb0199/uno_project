import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Uno extends Application {
    private Player player1;
    private Player computer;
    private Player turn_player;
    private Player target_player;
    private Deck deck;
    private Card played_card;
    private Stage game_stage = new Stage();
    private Deck pile;
    private int draw = 0;
    private HBox computer_cards;
    private HBox player1cards;
    private BorderPane root;
    private boolean hasDrawn;

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("Uno");

        Label label = new Label();
        label.setText("Welcome to UNO");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(20);
        grid.setHgap(20);

        Button start = new Button("New Game");
//        start.setText("New Game");
        grid.setConstraints(start, 0, 1);

        Button exit = new Button();
        exit.setText("Exit");
        grid.setConstraints(exit, 1, 1);

        start.setOnAction(e -> {
            window.close();
            startGame();
            displayGame();
            nextTurn();
        });
        exit.setOnAction(e -> window.close());

        grid.setAlignment(Pos.CENTER);
        grid.getChildren().addAll(start, exit);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, grid);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 600, 500);
        // window.setOnCloseRequest();
        window.setScene(scene);
        window.show();

    }
    private void startGame(){
        deck = new Deck();
        player1 = new Player("Player 1");
        target_player = player1;
        computer = new Player("Computer");
        turn_player = computer;
        deal();
        pile = new Deck(true);
        while (!(played_card instanceof NumberedCard)) {
            pile.add(deck.getTopCard());
//            played_card = pile.get(pile.size()-1);
            played_card = new NumberedCard("blue", 7);
        }
        draw = 0;
        hasDrawn = false;
    }
    private void deal(){
//        for (int i = 1; i <= 7; i++) {
//            player1.draw(deck.getTopCard());
//            computer.draw(deck.getTopCard());
//        }
        player1.draw(new NumberedCard("blue", 7));
        computer.draw(new NumberedCard("blue", 7));
    }

//    Do gameplay actions: skip/reverse, drawing, and switching turn_player and target_player

    private void nextTurn(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!(hasDrawn)) {
            doAction(played_card);
        } else {
            swapPlayers();
        }
        refreshLayout(turn_player);

        if (target_player.handSize() == 0) {
            if (draw > 0) {
                if (hasValidMove(turn_player)) {
                    if (turn_player == computer) {
                        computerTurn();
                    }
                    hasDrawn = false;
                } else {
                    playerDraw(turn_player, draw);
                    hasDrawn = true;

                    PopUpWindow.display("Game Over. " + target_player.toString() + " has won");
                    endGame();
                }

            } else {
                PopUpWindow.display("Game Over. " + target_player.toString() + " has won");
                endGame();
            }
        } else if (turn_player.handSize() == 0) {
            PopUpWindow.display("Game Over. " + turn_player.toString() + " has won");
            endGame();
        } else {
            if (hasValidMove(turn_player)) {
                if (turn_player == computer) {
                    computerTurn();
                } else {
                    hasDrawn = false;
                }
            } else {
                if (draw > 0) {
                    playerDraw(turn_player, draw);
                } else {
                    playerDraw(turn_player, 1);
                }
            }
        }
    }

    private void refreshLayout(Player player){
        if (player == player1) {
            player1cards.getChildren().clear();
        } else {
            computer_cards.getChildren().clear();
        }
        for (int i = 0; i < player.handSize(); i++) {
            addCardToLayout(player, player.get(i), i);
        }
    }

    private void swapPlayers(){
        Player temp = turn_player;
        turn_player = target_player;
        target_player = temp;
    }

    private void endGame(){
        player1cards = null;
        computer_cards = null;
        player1 = null;
        computer = null;
        game_stage.close();
        try {
            start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayGame(){
        root = new BorderPane();

        ImageView deck_pic = new ImageView("/cards/card_back.png");
        deck_pic.setFitHeight(200);
        deck_pic.setPreserveRatio(true);
        root.setRight(deck_pic);

        player1cards = new HBox(10);
        player1cards.setPadding(new Insets(10, 10, 10, 10));
        player1cards.setAlignment(Pos.CENTER);

        for (int i = 0; i < player1.handSize(); i++) {
            addCardToLayout(player1, player1.get(i), i);
        }
        root.setBottom(player1cards);

        computer_cards = new HBox(10);
        computer_cards.setPadding(new Insets(10, 10, 10, 10));
        computer_cards.setAlignment(Pos.CENTER);

        for (int i = 0; i < computer.handSize(); i++) {
            addCardToLayout(computer, computer.get(i), i);
        }
        root.setTop(computer_cards);

        Button quit_button = new Button("Quit Game");
        quit_button.setAlignment(Pos.CENTER);
        quit_button.setOnAction((ActionEvent a) -> {
            game_stage.close();
            try {
                start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        root.setLeft(quit_button);

        ImageView played_card_pic = new ImageView("/cards/" + played_card.toString() + ".png");
        played_card_pic.setFitHeight(200);
        played_card_pic.setPreserveRatio(true);
        root.setCenter(played_card_pic);

        Scene scene = new Scene(root, 1100, 900);
        game_stage.setScene(scene);
        game_stage.show();

    }
//    Checks if at least 1 of turn_player's cards is a valid move
    private boolean hasValidMove(Player player){
        for (int i = 0; i < player.handSize(); i++) {
            if (isValidMove(player.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidMove(Card card){
        if (draw > 0) {
            if (card instanceof ActionCard) {

                ActionCard act_card = (ActionCard) card;
                if (act_card.getAction().equals("draw2") || act_card.getColor().equals(played_card.getColor())) {
                    return true;
                }

            } else if (card instanceof WildCard) {

                WildCard wild_card = (WildCard) card;
                if (wild_card.isDraw4()) {
                    return true;
                }

            } else {
                return false;
            }
        } else {
            if (card instanceof WildCard) {
                return true;
            } else if (card.getColor().equals(played_card.getColor())) {
                return true;
            } else if (card instanceof ActionCard && played_card instanceof ActionCard) {

                ActionCard act_card = (ActionCard) card;
                ActionCard act_played_card = (ActionCard) played_card;
                if (act_card.getAction().equals(act_played_card.getAction())) {
                    return true;
                }

            } else if (card instanceof NumberedCard && played_card instanceof NumberedCard) {

                NumberedCard num_card = (NumberedCard) card;
                NumberedCard num_played_card = (NumberedCard)played_card;
                if (num_card.getNumber() == num_played_card.getNumber()){
                    return true;
                }

            }
        }
        return false;
    }

    private void addCardToLayout(Player player, Card card, int index){
        if (player == player1) {
// Make sure this correct on initial display
            ImageView card_pic = new ImageView("/cards/" + card.toString() + ".png");
            card_pic.setFitHeight(200);
            card_pic.setPreserveRatio(true);

            card_pic.setOnMouseClicked((MouseEvent m) -> {
                boolean is_valid_card = setMouseClickedHandler(index);
                if (is_valid_card) {
                    player1cards.getChildren().remove(index);
                    nextTurn();
                }
            });

            StackPane image_pane = new StackPane();
            image_pane.getChildren().add(card_pic);
            image_pane.setOnMouseEntered((MouseEvent m) -> image_pane.setStyle("-fx-border-color: black"));
            image_pane.setOnMouseExited((MouseEvent m) -> image_pane.setStyle(""));

            player1cards.getChildren().add(image_pane);

        } else {

            ImageView card_pic = new ImageView("/cards/card_back.png");
            card_pic.setFitHeight(200);
            card_pic.setPreserveRatio(true);
            computer_cards.getChildren().add(card_pic);

        }
    }

    private void playerDraw(Player player, int n){
        int hand_size = player.handSize();
        for (int i = hand_size; i < hand_size+n; i++) {
            Card card = deck.getTopCard();
            player.draw(card);
            addCardToLayout(player, card, i);
        }
        hasDrawn = true;
        draw = 0;
        nextTurn();
//        if (turn_player == player1) {
//
//            for (int i = player1.handSize(); i < player1.handSize()+n; i++) {
//                Card card = deck.getTopCard();
//                player1.draw(card);
//
//                ImageView card_pic = new ImageView("/cards/" + card.toString() + ".png");
//                card_pic.setFitHeight(200);
//                card_pic.setPreserveRatio(true);
//                StackPane image_pane = new StackPane();
//
//                int finalI = i;
//                card_pic.setOnMouseClicked((MouseEvent m) -> {
//                    boolean is_valid_card = setMouseClickedHandler(finalI);
//                    if (is_valid_card) {
//                        player1cards.getChildren().remove(finalI);
//                    }
//                });
//
//                image_pane.getChildren().add(card_pic);
//                image_pane.setOnMouseEntered((MouseEvent m) -> image_pane.setStyle("-fx-border-color: black"));
//                image_pane.setOnMouseExited((MouseEvent m) -> image_pane.setStyle(""));
//
//                player1cards.getChildren().add(image_pane);
//
//            }
//        } else {
//            for (int i = computer.handSize(); i < computer.handSize()+n; i++) {
//                computer.draw(deck.getTopCard());
//
//                ImageView card_pic = new ImageView("/cards/card_back.png");
//                card_pic.setFitHeight(200);
//                card_pic.setPreserveRatio(true);
//                computer_cards.getChildren().add(card_pic);
//            }
//        }
    }

    private void computerTurn(){
        for (int i = 0; i < computer.handSize(); i++) {
            Card card = computer.get(i);
            if (isValidMove(card)) {
                if (card instanceof WildCard) {
                    String[] colors = {"red", "yellow", "green", "blue"};
                    ((WildCard) card).setColor(colors[(int) (Math.random() * 4)]);
                }
                playCard(i);
//                turn ++;
                computer_cards.getChildren().remove(i);
                hasDrawn = false;
                break;

            }
        }
        nextTurn();
    }



    private boolean setMouseClickedHandler(int index){
        Card card = turn_player.get(index);
        if (isValidMove(card)) {
            playCard(index);
            return true;
        } else {
            if (draw > 2) {
                PopUpWindow.display(String.format("Invalid Move%s", ". Must play a draw card."));
            } else {
                PopUpWindow.display(String.format("Invalid Move%s", ". Cannot play that card."));
            }
            return false;
        }
    }

    private void playCard(int i) {
        pile.add(turn_player.remove(i));
        played_card = pile.get(pile.size()-1);

        ImageView played_card_pic = new ImageView("/cards/" + played_card.toString() + ".png");
        played_card_pic.setFitHeight(200);
        played_card_pic.setPreserveRatio(true);
        if (played_card instanceof WildCard) {
            StackPane image_pane = new StackPane();
            image_pane.getChildren().add(played_card_pic);
            image_pane.setStyle("-fx-border-color: " + played_card.getColor());
            root.setCenter(image_pane);
        } else {
            root.setCenter(played_card_pic);
        }
    }



    private void chooseColor(WildCard card, String color){
        card.setColor(color);
    }

    private void doAction(Card card){
        if (card instanceof WildCard) {
            WildCard wild_card = (WildCard) card;
            if (turn_player == player1) {
                String color = ChooseColorBox.display();
                chooseColor(wild_card, color);
            }
            if (wild_card.isDraw4()) {
                draw += 4;
            }
            swapPlayers();
        } else if (card instanceof ActionCard) {
            if (((ActionCard) card).getAction().equals("draw2")) {
                draw += 2;
                swapPlayers();
            }
        } else {
            swapPlayers();
        }
    }
////
}
//        boolean isValidMove = false;
//        String error_str = "";
//        if (draw > 0) {
//            if (card instanceof ActionCard) {
//
//                ActionCard act_card = (ActionCard) card;
//                if (act_card.getAction().equals("draw2")) {
//                    isValidMove = true;
//                    draw += 2;
//                }
//
//            } else if (card instanceof WildCard) {
//
//                WildCard wild_card = (WildCard) card;
//                if (wild_card.isDraw4()) {
//                    draw += 4;
//                    isValidMove = true;
//                }
//
//            } else {
//                isValidMove = false;
//                error_str = ". Must play a draw card.";
//            }
//        } else if (card.getColor().equals(played_card.getColor())) {
//            isValidMove = true;
//        } else if (card instanceof ActionCard) {
//
//            ActionCard act_card = (ActionCard) card;
//            ActionCard act_played_card = (ActionCard) played_card;
//            if (act_card.getAction().equals(act_played_card.getAction())) {
//                isValidMove = true;
//                if (act_card.getAction().equals("draw2")) {
//                    draw += 2;
//                }
//            }
//
//        } else if (card instanceof NumberedCard) {
//
//            NumberedCard num_card = (NumberedCard) card;
//            NumberedCard num_played_card = (NumberedCard)played_card;
//            if (num_card.getNumber() == num_played_card.getNumber()){
//                isValidMove = true;
//            }
//
//        } else if (card instanceof WildCard) {
//
//            WildCard wild_card = (WildCard) card;
//            isValidMove = true;
//            if (wild_card.isDraw4()) {
//                draw += 4;
//            }
//
//        }
