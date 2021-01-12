package ehu.isad.controllers.ui;

import ehu.isad.Main;
import ehu.isad.controllers.db.MainDBKud;
import ehu.isad.models.TaulaModel;
import ehu.isad.utils.Sarea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ResourceBundle;

public class TaulaKud implements Initializable {

    @FXML
    private TextField txtUrl;

    @FXML
    private Button btnCheck;

    @FXML
    private TableView<TaulaModel> tbTaula;

    @FXML
    private TableColumn<TaulaModel, String> url;

    @FXML
    private TableColumn<TaulaModel, String> md5;

    @FXML
    private TableColumn<TaulaModel, String> version;

    @FXML
    private Label lblInfo;

    private Main mainApp;

    public void setMainApp(Main main) {
        mainApp = main;
    }

    @FXML
    void onClick(ActionEvent event) {
        if (!txtUrl.getText().isBlank()) {
            String idatzitakoUrl = txtUrl.getText();
            String md5 = Sarea.getSarea().urlMd5Lortu(idatzitakoUrl);
            if (MainDBKud.getMainDBKud().md5Dago(md5)) {
                TaulaModel taulaModel = new TaulaModel(txtUrl.getText(), md5, MainDBKud.getMainDBKud().versionLortu(md5));
                tbTaula.getItems().add(taulaModel);
                lblInfo.setText("Datubasean zegoen");
            } else {
                TaulaModel taulaModel = new TaulaModel(txtUrl.getText(), md5, "");
                tbTaula.getItems().add(taulaModel);
                lblInfo.setText("Ez da datubasean aurkitu");
            }
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        tbTaula.setEditable(true);
        url.setCellValueFactory(new PropertyValueFactory<>("Url"));
        md5.setCellValueFactory(new PropertyValueFactory<>("Md5"));
        version.setCellValueFactory(new PropertyValueFactory<>("Version"));

        version.setCellFactory(TextFieldTableCell.forTableColumn());

        version.setOnEditCommit((TableColumn.CellEditEvent<TaulaModel, String> event) -> {
            TablePosition<TaulaModel, String> pos = event.getTablePosition();
            String bertsioBerria = event.getNewValue();
            int row = pos.getRow();
            TaulaModel taulaModel = event.getTableView().getItems().get(row);
            taulaModel.setVersion(bertsioBerria);
            this.versionEguneratu(taulaModel.getMd5(), bertsioBerria);
        });
    }

    private void versionEguneratu(String md5, String bertsioBerria) {
        if (!MainDBKud.getMainDBKud().md5Dago(md5)) {
            MainDBKud.getMainDBKud().gordeMd5(md5, bertsioBerria);
        } else {
            MainDBKud.getMainDBKud().versionEguneratu(md5, bertsioBerria);
            lblInfo.setText("md5 eta bertsio berria datubasean sartu dira");
        }
    }
}