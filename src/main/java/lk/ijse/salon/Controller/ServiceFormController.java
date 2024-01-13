package lk.ijse.salon.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.salon.bo.custom.ServiceBO;
import lk.ijse.salon.bo.custom.impl.ServiceBOImpl;
import lk.ijse.salon.dto.ServiceDto;
import lk.ijse.salon.dto.tm.ServiceTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class ServiceFormController {

        @FXML
        private TableColumn<?, ?> col_Des;

        @FXML
        private TableColumn<?, ?> col_SType;

        @FXML
        private TableColumn<?, ?> col_Sid;

        @FXML
        private TableColumn<?, ?> col_Sname;

        @FXML
        private TableColumn<?, ?> col_Sprice;

        @FXML
        private TableView<ServiceTm> table_service;

        ServiceBO serviceBO = new ServiceBOImpl();

        public void initialize(){
                setCellValueFactory();
                loadAllService();

        }

        private void setCellValueFactory() {
                col_Sid.setCellValueFactory(new PropertyValueFactory<>("s_id"));
                col_Sname.setCellValueFactory(new PropertyValueFactory<>("name"));
                col_Sprice.setCellValueFactory(new PropertyValueFactory<>("price"));
                col_SType.setCellValueFactory(new PropertyValueFactory<>("type"));
                col_Des.setCellValueFactory(new PropertyValueFactory<>("description"));
        }

        private void loadAllService() {
                try {
                        List<ServiceDto> serviceDtos = serviceBO.loadAllService();
                        ObservableList<ServiceTm> list = FXCollections.observableArrayList();
                        for (ServiceDto dto :serviceDtos) {
                                list.add(new ServiceTm(
                                        dto.getService_id(),
                                        dto.getService_name(),
                                        dto.getPrice(),
                                        dto.getService_type(),
                                        dto.getDescription()));
                        }
                        table_service.setItems(list);
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                }
        }

        @FXML
        void btnManageServicesonAction(ActionEvent event) {
                try {
                        URL resource = ServiceFormController.class.getResource("/view/ServiceManageForm.fxml");
                        Parent parent = FXMLLoader.load(resource);
                        Scene scene = new Scene(parent);
                        Stage stage = new Stage();
                        stage.setTitle("Mona Beauty Salon");
                        //stage.setAlwaysOnTop(true);
                        stage.setScene(scene);
                        stage.show();
                } catch (IOException e) {
                        e.printStackTrace();
                }

        }


}