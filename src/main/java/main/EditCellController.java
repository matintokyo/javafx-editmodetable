package main;

import com.tobiasdiez.easybind.EasyBind;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;

public class EditCellController {

    @FXML   private TableView<Item> bindingTableView;
    @FXML   private TableColumn<Item, String> bindingStringColumn;
    @FXML   private TableColumn<Item, Number> bindingNumberColumn;

    @FXML   private TableView<Item> tdiezTableView;
    @FXML   private TableColumn<Item, String> tdiezStringColumn;
    @FXML   private TableColumn<Item, Number> tdiezNumberColumn;

    @FXML   private TableView<Item> tmikulaTableView;
    @FXML   private TableColumn<Item, String> tmikulaStringColumn;
    @FXML   private TableColumn<Item, Number> tmikulaNumberColumn;

    @FXML   private TableView<Item> regularTableView;
    @FXML   private TableColumn<Item, String> regStringColumn;
    @FXML   private TableColumn<Item, Number> regNumberColumn;

    @FXML   private Label totalLabel;

    private final ObservableList<Item> itemList;

    public EditCellController(){
        itemList = FXCollections.observableArrayList(param -> new Observable[]{param.stringProperty(),param.integerProperty()});
    }

    @FXML
    void initialize() {
        buildUI();
        handleAddItem();
    }

    @FXML
    void handleAddItem(){
        Item item = new Item("abcde",1);
        itemList.add(item);
    }

    @FXML
    void handleDeleteBindingItem(){
        Item item = bindingTableView.getSelectionModel().getSelectedItem();
        if (item != null) {
            itemList.remove(item);
        }
    }

    @FXML
    void handleDeleteTdiezItem(){
        Item item = tdiezTableView.getSelectionModel().getSelectedItem();
        if (item != null) {
            itemList.remove(item);
        }
    }

    @FXML
    void handleDeleteTmikulaItem(){
        Item item = tmikulaTableView.getSelectionModel().getSelectedItem();
        if (item != null) {
            itemList.remove(item);
        }
    }

    private void buildUI() {

        buildEditModeBinding();
        buildEditModeEasybindTomasMikula();
        buildEditModeEasybindTobiasDiez();
        buildRegularTable();

        totalLabel.textProperty().bind(
                Bindings.convert(
                        EasyBind.reduce(itemList, stream -> stream.mapToInt(Item::getInteger).sum())
                )
        );
    }

    private void buildRegularTable() {
        regularTableView.itemsProperty().set(itemList);
        regStringColumn.setCellValueFactory(i -> i.getValue().stringProperty());
        regNumberColumn.setCellValueFactory(i -> i.getValue().integerProperty());
    }

    private void buildEditModeEasybindTobiasDiez() {

        tdiezTableView.itemsProperty().set(itemList);

        tdiezStringColumn.setCellValueFactory(cellData -> cellData.getValue().stringProperty());
        tdiezStringColumn.setCellFactory(col ->{

            TableCell<Item,String> cell = new TableCell<>();
            TextField textField = new TextField();
            textField.setPromptText("text");
            textField.getStyleClass().add("text-field-table-cell");

            cell.graphicProperty().bind(Bindings.when(cell.emptyProperty())
                    .then((Node)null)
                    .otherwise(textField));

            textField.textProperty().bindBidirectional(
                   EasyBind.wrapNullable(
                           EasyBind.select(cell.tableRowProperty())
                                   .selectObject(TableRow::itemProperty))
                           .selectProperty(Item::stringProperty)
           );

            return cell;

        });

        tdiezNumberColumn.setCellValueFactory(o -> o.getValue().integerProperty());
        tdiezNumberColumn.setCellFactory(col -> {

            TableCell<Item,Number> cell = new TableCell<>();
            TextField textField = new TextField();
            textField.setPromptText("integer");
            textField.getStyleClass().add("text-field-table-cell");
            TextFormatter<Number> tf = new TextFormatter<>(new NumberStringConverter());
            textField.setTextFormatter(tf);

            cell.graphicProperty().bind(Bindings.when(cell.emptyProperty())
                    .then((Node)null)
                    .otherwise(textField));

            tf.valueProperty().bindBidirectional(
                    EasyBind.wrapNullable(
                                    EasyBind.select(cell.tableRowProperty())
                                            .selectObject(TableRow::itemProperty))
                            .selectProperty(Item::integerProperty)
            );

            return cell ;
        });
    }

    private void buildEditModeEasybindTomasMikula() {
        tmikulaTableView.itemsProperty().set(itemList);

        tmikulaStringColumn.setCellValueFactory(cellData -> cellData.getValue().stringProperty());
        tmikulaStringColumn.setCellFactory(col ->{

            TableCell<Item,String> cell = new TableCell<>();
            TextField textField = new TextField();
            textField.setPromptText("text");
            textField.getStyleClass().add("text-field-table-cell");

            cell.graphicProperty().bind(Bindings.when(cell.emptyProperty())
                    .then((Node)null)
                    .otherwise(textField));

            textField.textProperty().bindBidirectional(
                    org.fxmisc.easybind.EasyBind.monadic(cell.tableRowProperty())
                            .selectProperty(TableRow::itemProperty)
                            .selectProperty(Item::stringProperty));
            return cell;

        });

        tmikulaNumberColumn.setCellValueFactory(o -> o.getValue().integerProperty());
        tmikulaNumberColumn.setCellFactory(col -> {

            TableCell<Item,Number> cell = new TableCell<>();
            TextField textField = new TextField();
            textField.setPromptText("integer");
            textField.getStyleClass().add("text-field-table-cell");
            TextFormatter<Number> tf = new TextFormatter<>(new NumberStringConverter());
            textField.setTextFormatter(tf);

            cell.graphicProperty().bind(Bindings.when(cell.emptyProperty())
                    .then((Node)null)
                    .otherwise(textField));

            tf.valueProperty().bindBidirectional(
                            org.fxmisc.easybind.EasyBind.monadic(cell.tableRowProperty())
                                    .selectProperty(TableRow::itemProperty)
                                    .selectProperty(Item::integerProperty));

            return cell ;
        });

    }

    private void buildEditModeBinding() {
        bindingTableView.itemsProperty().set(itemList);

        bindingStringColumn.setCellValueFactory(cellData -> cellData.getValue().stringProperty());
        bindingStringColumn.setCellFactory(col ->{

            TableCell<Item,String> cell = new TableCell<>();
            TextField textField = new TextField();
            textField.setPromptText("text");
            textField.getStyleClass().add("text-field-table-cell");

            cell.graphicProperty().bind(Bindings.when(cell.emptyProperty())
                    .then((Node)null)
                    .otherwise(textField));

            ChangeListener<Item> rowItemListener = (obs, oldItem, newItem) -> {
                if (oldItem != null) {
                    textField.textProperty().unbindBidirectional(((Item) oldItem).stringProperty());
                }
                if (newItem != null) {
                    textField.textProperty().bindBidirectional(((Item) newItem).stringProperty());
                }
            };
            cell.tableRowProperty().addListener((obs, oldRow, newRow) -> {
                if (oldRow != null) {
                    oldRow.itemProperty().removeListener(rowItemListener);
                    if (oldRow.getItem() != null) {
                        textField.textProperty().unbindBidirectional(((Item) oldRow.getItem()).stringProperty());
                    }
                }
                if (newRow != null) {
                    newRow.itemProperty().addListener(rowItemListener);
                    if (newRow.getItem() != null) {
                        textField.textProperty().bindBidirectional(((Item) newRow.getItem()).stringProperty());
                    }
                }
            });

            return cell;

        });


        bindingNumberColumn.setCellValueFactory(o -> o.getValue().integerProperty());
        bindingNumberColumn.setCellFactory(col -> {

            TableCell<Item,Number> cell = new TableCell<>();
            TextField textField = new TextField();
            textField.setPromptText("integer");
            textField.getStyleClass().add("text-field-table-cell");
            TextFormatter<Number> tf = new TextFormatter<>(new NumberStringConverter());
            textField.setTextFormatter(tf);

            cell.graphicProperty().bind(Bindings.when(cell.emptyProperty())
                    .then((Node)null)
                    .otherwise(textField));

            ChangeListener<Item> rowItemListener = (obs, oldItem, newItem) -> {
                if (oldItem != null) {
                    tf.valueProperty().unbindBidirectional(((Item) oldItem).integerProperty());
                }
                if (newItem != null) {
                    tf.valueProperty().bindBidirectional(((Item) newItem).integerProperty());
                }
            };
            cell.tableRowProperty().addListener((obs, oldRow, newRow) -> {
                if (oldRow != null) {
                    oldRow.itemProperty().removeListener(rowItemListener);
                    if (oldRow.getItem() != null) {
                        tf.valueProperty().unbindBidirectional(((Item) oldRow.getItem()).integerProperty());
                    }
                }
                if (newRow != null) {
                    newRow.itemProperty().addListener(rowItemListener);
                    if (newRow.getItem() != null) {
                        tf.valueProperty().bindBidirectional(((Item) newRow.getItem()).integerProperty());
                    }
                }
            });

            return cell ;
        });
    }

    private class Item{
        StringProperty string = new SimpleStringProperty("abcde");
        IntegerProperty integer = new SimpleIntegerProperty(1);

        public Item(String string, Integer integer) {
            this.string.set(string);
            this.integer.set(integer);
        }

        public String getString() {
            return string.get();
        }
        public StringProperty stringProperty() {
            return string;
        }

        public int getInteger() {
            return integer.get();
        }
        public IntegerProperty integerProperty() {
            return integer;
        }

    }

}

