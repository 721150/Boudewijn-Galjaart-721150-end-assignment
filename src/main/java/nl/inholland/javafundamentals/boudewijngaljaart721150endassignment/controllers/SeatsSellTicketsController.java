package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Show;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;

public class SeatsSellTicketsController {
    private User user;

    private Database database;

    private Show show;

    @FXML
    private GridPane allSeatsGridPane;

    @FXML
    private Label informationAboutSelectedShowLabel;

    @FXML
    private TextArea selectedSeatsTextArea;

    public void giveData(User user, Database database, Show show) {
        this.user = user;
        this.database = database;
        this.show = show;
        informationAboutSelectedShowLabel.setText(this.show.getStartDateTime() + " " + this.show.getTitle());
        loadSeatsInGridPane();
    }

    private void loadSeatsInGridPane() {
        // Maak de GridPane met daarin alle zitplaatsen voor de voorstelling
        allSeatsGridPane.setHgap(5);
        allSeatsGridPane.setVgap(5);
        for (int row = 0; row < this.show.getSeats().length; row++) {
            Text rowLabel = new Text("Row " + (row + 1));
            allSeatsGridPane.add(rowLabel, 0, row);
            for (int col = 0; col < this.show.getSeats()[row].length; col++) {
                Rectangle seat  = new Rectangle(30,30);

                Text text = new Text(String.valueOf((col + 1)));
                text.setFill(Color.WHITE);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(seat, text);

                if (this.show.getSeats()[row][col] != null){
                    seat.setFill(Color.RED);
                }
                else {
                    seat.setFill(Color.GRAY);
                }


                final int currentRow = row + 1;
                final int currentCol = col + 1;
                stackPane.setOnMouseClicked(event -> {
                    Color currentColor = (Color)seat.getFill();
                    if (currentColor == Color.RED) {
                        // Foutmelding laten weergeven
                    }
                    else if (currentColor == Color.GREEN) {
                        seat.setFill(Color.GRAY);
                        selectedSeatsTextArea.setText(selectedSeatsTextArea.getText().replace("Row " + currentRow + "/Seat " + currentCol + "\n", ""));
                    }
                    else {
                        seat.setFill(Color.GREEN);
                        selectedSeatsTextArea.appendText("Row " + currentRow + "/Seat " + currentCol + "\n");
                    }
                });

                allSeatsGridPane.add(stackPane, col + 1, row);
            }
        }
    }
}
