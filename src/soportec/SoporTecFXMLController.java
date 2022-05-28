package soportec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class SoporTecFXMLController implements Initializable {

    ObservableList<String> tipoSoporteList = FXCollections
            .observableArrayList(
                    "Tecnico en Reparacion de PCs",
                    "Tecnico de Aplicativos",
                    "Tecnico de Redes e Internet"
            );

    // Form controls
    @FXML
    private ChoiceBox tipoSoporte;
    @FXML
    private TextField dpiField;
    @FXML
    private TextField nombreField;
    @FXML
    private Button guardarButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button cargarcsvButton;

    // Linked List
    private static LinkedList listaEmpleados;

    // TableView Stuff
    @FXML
    private TableView<Node> empleadosTable;
    @FXML
    private TableColumn<Node, String> dpiColumn;
    @FXML
    private TableColumn<Node, String> nombreColumn;
    @FXML
    private TableColumn<Node, String> tipoSoporteColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaEmpleados = new LinkedList();
        tipoSoporte.setItems(tipoSoporteList);
        dpiColumn.setCellValueFactory(new PropertyValueFactory<>("dpi"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre_completo"));
        tipoSoporteColumn.setCellValueFactory(new PropertyValueFactory<>("tipo_soporte"));
        addDeleteButtonToTable();
        addUpdateButtonToTable();
    }

    private void recorrerListaYActualizarTabla() {
        ObservableList<Node> empleados = FXCollections.observableArrayList();
        Node currNode = listaEmpleados.head;
        while (currNode != null) {
            empleados.add(currNode);
           
            currNode = currNode.next;
            
        }
        empleadosTable.setItems(empleados);
    }

    private void addDeleteButtonToTable() {
        TableColumn<Node, Void> colBtn = new TableColumn("Eliminar");

        Callback<TableColumn<Node, Void>, TableCell<Node, Void>> cellFactory = (final TableColumn<Node, Void> param) -> {
            final TableCell<Node, Void> cell = new TableCell<Node, Void>() {

                private final Button btn = new Button("Eliminar");

                {
                    btn.setOnAction((ActionEvent event) -> {
                        Node data = getTableView().getItems().get(getIndex());
                        listaEmpleados = LinkedList.delete(listaEmpleados, data.dpi);
                        recorrerListaYActualizarTabla();
                        Guardarcsv();
                    });
                    
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        };

        colBtn.setCellFactory(cellFactory);

        empleadosTable.getColumns().add(colBtn);
    }

    private void addUpdateButtonToTable() {
        TableColumn<Node, Void> colBtn = new TableColumn("Modificar");

        Callback<TableColumn<Node, Void>, TableCell<Node, Void>> cellFactory = (final TableColumn<Node, Void> param) -> {
            final TableCell<Node, Void> cell = new TableCell<Node, Void>() {

                private final Button btn = new Button("Modificar");

                {
                    btn.setOnAction((ActionEvent event) -> {
                        Node data = getTableView().getItems().get(getIndex());
                        dpiField.setText(String.valueOf(data.dpi));
                        dpiField.setDisable(true);
                        nombreField.setText(data.nombre_completo);
                        tipoSoporte.setValue(data.tipo_soporte);
                        guardarButton.setVisible(false);
                        updateButton.setVisible(true);
                       
                    });
                     
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                     Guardarcsv();
                }
            };
            return cell;
        };

        colBtn.setCellFactory(cellFactory);

        empleadosTable.getColumns().add(colBtn);
    }
    
    private void limpiarCampos() {
        dpiField.setText("");
        nombreField.setText("");
        tipoSoporte.setValue("");
    }
    

    @FXML
    private void handleInsert(ActionEvent event) {
        LinkedList.insert(
                listaEmpleados,
                Long.parseLong(dpiField.getText()),
                nombreField.getText(),
                tipoSoporte.getValue().toString()
        );
        recorrerListaYActualizarTabla();
      limpiarCampos();
       Guardarcsv();
    }
    
    

    @FXML
    private void handleUpdate(ActionEvent event) {

        listaEmpleados = LinkedList.update(
                listaEmpleados,
                Long.parseLong(dpiField.getText()),
                nombreField.getText(),
                tipoSoporte.getValue().toString()
        );
        recorrerListaYActualizarTabla();
        limpiarCampos();
        dpiField.setDisable(false);
        guardarButton.setVisible(true);
        updateButton.setVisible(false);    
    }
    
    @FXML
    private void cargarcsv(ActionEvent event) {
        System.out.println(System.getProperty("user.dir"));
         String FieldDelimiter = ";";
 
        BufferedReader br;
 
        try {
            br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\Listass.csv"));
 
            String line;
            while ((line = br.readLine()) != null) {
              
                String[] values = line.split(FieldDelimiter, -1);
                Node record = new Node(Long.parseLong(values[0]), values[1], values[2]);
              listaEmpleados= LinkedList.insert(listaEmpleados,Long.parseLong(values[0]), values[1], values[2]);
                recorrerListaYActualizarTabla();
 
            }
 
        } catch (Exception ex){
           System.out.println(ex.getMessage());
     }
        cargarcsvButton.setDisable(true);
        Guardarcsv();
    }
    
  public static void Guardarcsv (){
        BufferedWriter archivoescritura = null;
        String directorio = System.getProperty("user.dir") + "\\Listass.csv";
        try {
            archivoescritura = new BufferedWriter (new FileWriter (directorio));
              ObservableList<Node> empleados = FXCollections.observableArrayList();

        Node c2 = listaEmpleados.head;
        while (c2 != null) {
            empleados.add(c2);
            
            archivoescritura.write(c2.getDpi()+";" + c2.getNombre_completo()+ ";"+ c2.getTipo_soporte());
            archivoescritura.newLine();
            
            c2 = c2.next;
            
        }

        archivoescritura.close();
        } catch (Exception ex){
            
        }
    }
}
