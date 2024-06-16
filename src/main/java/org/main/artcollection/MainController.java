package org.main.artcollection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.main.artcollection.models.ArtPiece;
import org.main.artcollection.services.ArtCollectionManager;
import org.main.artcollection.services.UserMananger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainController {
    private Map<String, ArtPiece> artPieces;

    @FXML
    public Label titleView;
    @FXML
    public Label authorView;
    @FXML
    public Label descriptionView;
    @FXML
    public Label yearView;

    @FXML
    private Button closeButton;
    @FXML
    public Button closeButton2;
    @FXML
    private ChoiceBox<String> selectionList;

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField imageSelector;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Button addButton;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView imageView;

    @FXML
    private void closeAction(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void initialize() {
        this.updateArts();
        this.selectionList.getSelectionModel().selectedItemProperty().addListener((a, b, newItem) -> {
            ArtPiece piece = this.artPieces.get(newItem);
            try(InputStream stream = new FileInputStream(new File("images/" + piece.getPath()))) {
                Image image = new Image(stream);
                this.imageView.setImage(image);
            }
            catch (Exception e) {
            }

            this.titleView.setText(piece.getTitle());
            this.authorView.setText(piece.getArtist());
            this.yearView.setText(String.valueOf(piece.getYear()));
        });
    }

    private void showArt(ArtPiece artPiece) {

    }

    private void updateArts() {
        this.selectionList.getItems().clear();
        List<ArtPiece> artPieceList = new ArrayList<>();
        try {
            artPieceList = ArtCollectionManager.getAllArtPieces();
        } catch (IOException ex) {
        }


        this.artPieces = new HashMap<>();
        for (ArtPiece artPiece : artPieceList) {
            this.artPieces.put(artPiece.getTitle(), artPiece);
            this.selectionList.getItems().add(artPiece.getTitle());
        }
    }

    @FXML
    public void selectImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        File file = fileChooser.showOpenDialog(this.imageSelector.getScene().getWindow());
        this.imageSelector.setText(file.getAbsolutePath());
    }

    @FXML
    public void addArtPiece(ActionEvent event) {
        if (this.titleField.getText().isEmpty()) {
            this.errorLabel.setText("Title field is empty");
            return;
        }

        if (this.titleField.getText().isEmpty()) {
            this.errorLabel.setText("Author field is empty");
            return;
        }

        if (this.yearField.getText().isEmpty()) {
            this.errorLabel.setText("Year field is empty");
            return;
        }

        int year = 0;
        try {
            year = Integer.parseInt(this.yearField.getText());
        } catch (Exception ex) {
            this.errorLabel.setText("Year is not a number");
            return;
        }

        if (this.imageSelector.getText().isEmpty()) {
            this.errorLabel.setText("Image field is empty");
            return;
        }

        if (this.artPieces.get(titleField.getText()) != null) {
            this.errorLabel.setText("Art with same name already exists");
            return;
        }

        String name;
        try {
            Path original = Paths.get(this.imageSelector.getText());
            name = original.getFileName().toString();
            Path copied = Paths.get("images/" + name);
            Files.copy(original, copied, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            this.errorLabel.setText("Cannot copy image to database");
            return;
        }


        ArtPiece piece = new ArtPiece(this.titleField.getText(), this.authorField.getText(), year, this.descriptionField.getText(), name);
        try {
            ArtCollectionManager.addArtPiece(piece);
        } catch (IOException ex) {
            this.errorLabel.setText("Cannot add art piece to database");
        }
        this.updateArts();
    }
}