/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roy.controller;

import com.roy.MainApp;
import com.roy.dao.CategoryDaoImpl;
import com.roy.dao.ItemDaoImpl;
import com.roy.entity.Category;
import com.roy.entity.Item;
import com.roy.util.ViewUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author master
 */
public class MainFormController implements Initializable {

    @FXML
    private TextField txtid;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtprice;
    @FXML
    private TextArea txtDescription;
    @FXML
    private CheckBox checkRecommended;
    @FXML
    private TableColumn<Item, String> col01;
    @FXML
    private TableColumn<Item, String> col02;
    @FXML
    private ComboBox<Category> comboCategory;
    @FXML
    private TableColumn<Item, String> col03;
    @FXML
    private TableColumn<Item, Integer> col04;
    @FXML
    private TableColumn<Item, Boolean> col05;
    private Stage categoryStage;
    @FXML
    private BorderPane root;
    private ItemDaoImpl itemDao;
    private CategoryDaoImpl categoryDao;
    private ObservableList<Category> categorys;
    private ObservableList<Item> items;
    @FXML
    private TableView<Item> tableItem;
    private Item selectedItem;

    public ObservableList<Item> getItems() throws SQLException {
        if (items == null) {
            items = FXCollections.observableArrayList();
            items.addAll(getItemDao().getAllData());
        }
        return items;
    }

    public ItemDaoImpl getItemDao() {
        if (itemDao == null) {
            itemDao = new ItemDaoImpl();
        }
        return itemDao;
    }

    public ObservableList<Category> getCategories() throws SQLException {

        categorys = FXCollections.observableArrayList();
        categorys.addAll(getCategoryDao().getAllData());

        return categorys;
    }

    public CategoryDaoImpl getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryDaoImpl();
        }
        return categoryDao;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryDao = new CategoryDaoImpl();
        categorys = FXCollections.observableArrayList();
        try {
            categorys.addAll(categoryDao.getAllData());
        } catch (SQLException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        comboCategory.setItems(categorys);

        itemDao = new ItemDaoImpl();
        items = FXCollections.observableArrayList();
        try {
            items.addAll(itemDao.getAllData());
        } catch (SQLException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        tableItem.setItems(items);

        col01.setCellValueFactory(new PropertyValueFactory<>("id"));
        /*
         col01.setCellValueFactory((data) -> {
         Item item = (Item) data.getValue();
         return new SimpleStringProperty(item.getId());
         });
         */
        col02.setCellValueFactory((data) -> {
            Item item = (Item) data.getValue();
            return new SimpleStringProperty(item.getName());
        });

        col03.setCellValueFactory((data) -> {
            Item item = (Item) data.getValue();
            return new SimpleStringProperty(item.getCategor().getName());
        });
//
//        col04.setCellValueFactory((data) -> {
//            Item item = (Item) data.getValue();
//            return new SimpleIntegerProperty(item.getPrice());
//        });

        col04.setCellValueFactory(new PropertyValueFactory<>("price"));

        col05.setCellValueFactory(new PropertyValueFactory<>("recomended"));
//
//        col05.setCellValueFactory((data) -> {
//            Item item = (Item) data.getValue();
//            return new SimpleBooleanProperty(item.);
//        });
    }

    @FXML
    private void saveAction(ActionEvent event) {
        boolean duplicate = false;
        if (txtid.getText().trim().isEmpty() || txtName.getText().trim().
                isEmpty() || txtprice.getText().trim().isEmpty()
                || comboCategory.getValue() == null) {
            ViewUtil.showAlert(Alert.AlertType.ERROR, "Error",
                    "Please fill all field");
        } else {
            try {
                for (int i = 0; i < getItems().size(); i++) {
                    try {
                        if (getItems().get(i).getName().equals(txtid.getText())
                                || getItems().get(i).getName().equals(txtName.
                                        getText())) {
                            duplicate = true;
                            break;
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(MainFormController.class.getName()).
                                log(Level.SEVERE, null, ex);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainFormController.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
            if (!duplicate) {
                Item item = new Item();
                item.setId(Integer.valueOf(txtid.getText()));
                item.setName(txtName.getText());
                item.setPrice(Double.valueOf(txtprice.getText()));
                item.setDescription(txtDescription.getText());
                item.setRecomended(checkRecommended.isSelected());
                item.setCategor(comboCategory.getValue());

                try {
                    getItemDao().addData(item);
                    getItems().clear();
                    getItems().addAll(getItemDao().getAllData());
                } catch (SQLException ex) {
                    Alert a = new Alert(Alert.AlertType.NONE);
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setContentText("Duplicate Id");
                    a.show();
                }
                tableItem.refresh();
            } else {
                ViewUtil.showAlert(Alert.AlertType.ERROR, "Error",
                        "Duplicate item id/name");
            }
        }
    }

    @FXML
    private void resetAction(ActionEvent event) {
        txtid.clear();
        txtName.clear();
        txtprice.clear();
        txtDescription.clear();
        checkRecommended.setManaged(false);
    }

    @FXML
    private void updateAction(ActionEvent event) {
        Item item = new Item();
        item.setId(Integer.parseInt(txtid.getText()));
        item.setName(txtName.getText());
        item.setPrice(Double.valueOf(txtprice.getText()));
        item.setDescription(txtDescription.getText());
        item.setRecomended(checkRecommended.isSelected());
        item.setCategor(comboCategory.getValue());

        try {
            getItemDao().updateData(item);
            getItems().clear();
            getItems().addAll(getItemDao().getAllData());
        } catch (SQLException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        tableItem.refresh();
    }

    @FXML
    private void deleteAction(ActionEvent event) {
        selectedItem = tableItem.getSelectionModel().getSelectedItem();

        try {
            getItemDao().deleteData(selectedItem);
            getItems().clear();
            getItems().addAll(getItemDao().getAllData());
        } catch (SQLException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void tableCliked(MouseEvent event) {
        selectedItem = tableItem.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtid.setText(String.valueOf(selectedItem.getId()));
            txtName.setText(selectedItem.getName());
            txtprice.setText(String.valueOf(selectedItem.getPrice()));
            txtDescription.setText(selectedItem.getDescription());
            checkRecommended.setSelected(selectedItem.isRecomended());
            comboCategory.setValue(selectedItem.getCategor());
        }
    }

    @FXML
    private void showCategoryAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Category.fxml"));
            BorderPane pane = loader.load();
            Scene scene = new Scene(pane);
            categoryStage = new Stage();
            categoryStage.setScene(scene);
            categoryStage.setTitle("JavaFx Stage");
            categoryStage.initModality(Modality.APPLICATION_MODAL);
        } catch (IOException ex) {
            Logger.getLogger(MainFormController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        categoryStage.show();
    }

    @FXML
    private void closeAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void helpAction(ActionEvent event) {
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        alertInfo.setContentText("Created by royparsaoran 1772044");
        alertInfo.setTitle("Information");
        alertInfo.showAndWait();
    }

    @FXML
    private void categoryClick(MouseEvent event) {
        try {
            comboCategory.setItems(getCategories());
        } catch (SQLException ex) {
            Logger.getLogger(MainFormController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

}
