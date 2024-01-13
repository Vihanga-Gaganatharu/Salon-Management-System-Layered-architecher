package lk.ijse.salon.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.salon.bo.custom.CustomersBO;
import lk.ijse.salon.bo.custom.impl.CustomersBOImpl;
import lk.ijse.salon.db.DbConnection;
import lk.ijse.salon.dto.CustomerDto;
import lk.ijse.salon.dto.tm.CustomerTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {

        @FXML
        private TableColumn<?, ?> col_address;

        @FXML
        private TableColumn<?, ?> col_gender;

        @FXML
        private TableColumn<?, ?> col_id;

        @FXML
        private TableColumn<?, ?> col_name;

        @FXML
        private TableColumn<?, ?> col_nic;

        @FXML
        private TableColumn<?, ?> col_phno;

        @FXML
        private TableView<CustomerTm> tbl_cus;

        @FXML
        private TextField txtGenreportsCusId;
        CustomersBO customersBO = new CustomersBOImpl();

        public void initialize(){
                setCellValueFactory();
                loadAllCustomer();

        }

        private void setCellValueFactory() {
                col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
                col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
                col_nic.setCellValueFactory(new PropertyValueFactory<>("nic"));
                col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
                col_phno.setCellValueFactory(new PropertyValueFactory<>("phoneno"));
                col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        }


        private void loadAllCustomer() {
                try {
                        List<CustomerDto> customerDtos = customersBO.loadAllCustomers();
                        ObservableList<CustomerTm> list = FXCollections.observableArrayList();
                        for (CustomerDto dto :customerDtos) {
                                list.add(new CustomerTm(dto.getC_id(), dto.getFirst_name(), dto.getNic(), dto.getAddress(), dto.getPhone_number(), dto.getGender()));
                        }
                        tbl_cus.setItems(list);
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                }
        }

        @FXML
        void btnManageCustOnAction(ActionEvent event) {
                try {
                        URL resource = CustomerManageFormController.class.getResource("/View/CustomerManageForm.fxml");
                        Parent parent = FXMLLoader.load(resource);
                        Scene scene = new Scene(parent);
                        Stage stage = new Stage();
                        stage.setTitle("Mona Beauty Salon");
                       // stage.setAlwaysOnTop(true);
                        stage.setScene(scene);
                        stage.show();
                } catch (IOException e) {
                        e.printStackTrace();
                }

        }

        @FXML
        void ReportsOnAction() throws SQLException, JRException {
                InputStream resourceAsStream = getClass().getResourceAsStream("/Reports/customerReport.jrxml");
                JasperDesign load = JRXmlLoader.load(resourceAsStream);
                JRDesignQuery jrDesignQuery = new JRDesignQuery();
                jrDesignQuery.setText("SELECT * FROM customers WHERE c_id = "+"\""+txtGenreportsCusId.getText()+"\"");
                load.setQuery(jrDesignQuery);
                JasperReport jasperReport = JasperCompileManager.compileReport(load);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, DbConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasperPrint,false);
        }

        public void btnReportOnAction(ActionEvent actionEvent) throws JRException, SQLException {
                ReportsOnAction();
        }

        public void btnSearchCusdOnAction(ActionEvent actionEvent) {
                String id = txtGenreportsCusId.getText();

                try {
                        CustomerDto dto = customersBO.searchCustomer(id);
                        if(dto != null) {
                                fillFields(dto);
                        } else {
                                new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
                        }
                } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
        }
        private void fillFields(CustomerDto dto) {
        }
}


