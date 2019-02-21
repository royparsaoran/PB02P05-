/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roy.controller;

import com.roy.dao.CategoryDaoImpl;
import com.roy.entity.Category;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author royparsaoran 1772044
 */
public class CategoryController implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private TableView<Category> categoryName;
    @FXML
    private TableColumn<Category, String> colID;
    @FXML
    private TableColumn<Category, String> colCategory;
    @FXML
    private Button saveCategory;
    @FXML
    private TextField txtIDCategory;
    @FXML
    private TextField txtnameCategory;
    private MainFormController mainFormController;

    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private ObservableList<Category> categorys;
    private CategoryDaoImpl categoryDao;
    private MainFormController mainController;
    private ObservableList<Category> categories;

    public void setMainController(MainFormController mainController) throws
            SQLException {
        this.mainController = mainController;
        categoryName.setItems(mainController.getCategories());
    }

    @Override

    public void initialize(URL url, ResourceBundle rb) {
        Show();
    }

    public void Show() {
        try {
            categoryDao = new CategoryDaoImpl();
            categories = FXCollections.
                    observableArrayList();

            categories.addAll(categoryDao.getAllData());
            categoryName.setItems(categories);

            colID.setCellValueFactory(data
                    -> {
                Category category = data.getValue();
                return new SimpleStringProperty(String.valueOf(category.getId()));
            }
            );
            colCategory.setCellValueFactory(data
                    -> {
                Category category = data.getValue();
                return new SimpleStringProperty(String.valueOf(category.
                        getName()));
            }
            );
        } catch (SQLException ex) {
            Logger.getLogger(CategoryController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public CategoryDaoImpl getCategoriesDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryDaoImpl();
        }
        return categoryDao;
    }

    public ObservableList<Category> getCategories() {
        try {
            categories = FXCollections.observableArrayList();
            categories.addAll(getCategoriesDao().getAllData());

        } catch (SQLException ex) {
            Logger.getLogger(CategoryController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    @FXML
    private void btnSave(ActionEvent event) {
        Category category = new Category();
        category.setId(Integer.valueOf(txtIDCategory.getText()));
        category.setName(txtnameCategory.getText().trim());
        try {
            categoryDao.addData(category);
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Duplicate ID Category");
            a.show();
        }

        getCategories().clear();
        try {
            getCategories().addAll(getCategoriesDao().getAllData());
        } catch (SQLException ex) {
            Logger.getLogger(CategoryController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        Show();
    }

}
