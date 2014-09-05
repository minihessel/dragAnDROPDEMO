/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dragandropdemo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 *
 * @author Eskil Hesselroth
 */
public class FXMLDocumentController implements Initializable {

    List<String> employees;
    @FXML
    private Label label;
    @FXML
    private Label label2;

    @FXML
    private TreeView treeView;
    @FXML
    private TreeView treeView2;
       private ContextMenu rootContextMenu;

    @FXML
    private TextField textField;
    private static TreeItem DRAGGEDSOURCE;
    private static TreeItem DRAGGEDTARGET;
      private static TreeCell RENAMETARGET;

    TreeItem<String> rootNode
            = new TreeItem<String>("New Table");
    TreeItem<String> rootNode2
            = new TreeItem<String>("New Table");

    TreeItem<String> kolonne
            = new TreeItem<String>("Kolonne 1");

    TreeItem<String> kolonne2
            = new TreeItem<String>("Kolonne 2");

    @FXML
    private void handleButtonAction(ActionEvent event) {

        treeView2.getRoot().getChildren().add(new TreeItem<String>(textField.getText().toString()));

    }
    
    

    private void addDragAndDrop(TreeCell<String> treeCell) {
        
        
    /*    treeCell.setOnMouseClicked(new EventHandler<MouseEvent>(){
         @Override
            public void handle(MouseEvent event) {
                treeView.setEditable(true);
            treeView.edit(treeCell.getTreeItem());
            
                          }
            
        
            
            
           
        });**/
        treeCell.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("setOnDragDetected");

                Dragboard db = treeCell.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();

                content.putString(treeCell.getText());
                DRAGGEDSOURCE = treeCell.getTreeItem();
                db.setContent(content);
                event.consume();

            }
        });

        treeCell.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {

                DRAGGEDTARGET = treeCell.getTreeItem();
                if (event.getGestureSource() != treeCell
                        && event.getDragboard().hasString()) {
                    System.out.println("DU ER OVER");

                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        treeCell.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {

                /* data dropped */
                System.out.println("DROPPEd");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();

                boolean success = false;

                System.out.println(event.getSource());

                DRAGGEDTARGET.getChildren().add(DRAGGEDSOURCE);
                    //  rootNode2.getChildren().add(DRAGGEDSOURCE);

                        // if (db.hasString()) {
                //     target.setText(db.getString());
                //  success = true;
                //  }
                        /* let the source know whether the string was successfully
                 * transferred and used */
                // treeView2.getRoot().getChildren().add(DRAGGEDTARGET);
                event.setDropCompleted(success);

                event.consume();
            }

        });
    }
    
    
        private final class TextFieldTreeCellImpl extends TreeCell<String> {
 
        private TextField textField;
 
        public TextFieldTreeCellImpl() {
        }
 
        @Override
        public void startEdit() {
            super.startEdit();
 
            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());
        }
 
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                }
            }
        }
 
        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
 
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final Text text1 = new Text(50, 100, "TEXT 1");
        text1.setScaleX(2.0);
        text1.setScaleY(2.0);

        final Text text2 = new Text(250, 100, "TEXT 2");
        text2.setScaleX(2.0);
        text2.setScaleY(2.0);

        final Text text3 = new Text(50, 200, "TEXT 3");
        text3.setScaleX(2.0);
        text3.setScaleY(2.0);

        final Text text4 = new Text(250, 200, "TEXT 4");
        text4.setScaleX(2.0);
        text4.setScaleY(2.0);

        rootNode.getChildren().add(kolonne);
        rootNode.getChildren().add(kolonne2);

        treeView.rootProperty().set(rootNode);

        treeView2.rootProperty().set(rootNode2);

        treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> stringTreeView) {
                TreeCell<String> treeCell = new TreeCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item != null) {
                            setText(item);
                            setGraphic(getTreeItem().getGraphic());
                            
                        } else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };

                addDragAndDrop(treeCell);

                return treeCell;
            }
        });

        treeView2.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> stringTreeView) {
                TreeCell<String> treeCell = new TreeCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item != null) {
                            setText(item);
                            setGraphic(getTreeItem().getGraphic());
                        } else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };

                addDragAndDrop(treeCell);

                return treeCell;
            }

        });

    }
}
