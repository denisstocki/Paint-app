package org.example; /**
 * IMPORT BIBLIOTEK
 */
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.input.*;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

/**
 * KLASA newEllipse - OBSLUGUJE OBIEKT ELLIPSE
 */
public class NewEllipse extends Ellipse {

    /**
     * KONSTRUKTOR NewEllipse - TWORZY OBIEKT I WYWOLUJE EVENTY
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public NewEllipse(double x, double y, double width, double height){
        super(x, y, width, height);
        setOnMouseClicked(new EllipseMouseHandler());
        setOnMouseDragged(new EllipseMouseHandler());
        setOnScroll(new EllipseScrollHandler());
    }

    /**
     * FUNKCJA isHit - SPRAWDZA CZY DANY EVENT MIAL MIEJSCE NA OBSZARZE OBIEKTU
     * @param x
     * @param y
     * @return
     */
    public boolean isHit(double x, double y){
        return getBoundsInLocal().contains(x, y);
    }

    /**
     * FUNKCJA addX - PRZESUWA STWORZONY OBIEKT PO OSI X
     * @param x
     */
    public void addX(double x){
        this.setCenterX(this.getCenterX()+x);
    }

    /**
     * FUNKCJA addY - PRZESUWA STWORZONY OBIEKT PO OSI Y
     * @param y
     */
    public void addY(double y){
        this.setCenterY(this.getCenterY()+y);
    }

    /**
     * FUNKJCA addWidth - ZMIENIA PROMIEN X STWORZONEGO OBIEKTU
     * @param w
     */
    public void addWidth(double w){
        this.setRadiusX(this.getRadiusX()+w);
    }

    /**
     * FUNKCJA addHeight - ZMIENIA PROMIEN Y STWORZONEGO OBIEKTU
     * @param h
     */
    public void addHeight(double h){
        this.setRadiusY(this.getRadiusY()+h);
    }

    /**
     * KLASA EllipseScrollHandler - OBSLUGUJE SCROLLEVENT
     */
    public class EllipseScrollHandler implements EventHandler<ScrollEvent>{
        NewEllipse newEllipse;

        //FUNKCJA doScale - ZMIENIA ROZMIAR OBIEKTU
        private void doScale(ScrollEvent scrollEvent){
            double x = scrollEvent.getX();
            double y = scrollEvent.getY();
            if(newEllipse.isHit(x, y)){
                newEllipse.addWidth(scrollEvent.getDeltaY()*0.2);
                newEllipse.addHeight(scrollEvent.getDeltaY()*0.2);
            }
        }

        //FUNKCJA handle - WYWOLUJE ZMIANE WIELKOSCI OBIEKTU
        @Override
        public void handle(ScrollEvent scrollEvent) {
            newEllipse = (NewEllipse) scrollEvent.getSource();
            if(scrollEvent.getEventType()==ScrollEvent.SCROLL){
                doScale(scrollEvent);
            }
        }
    }

    /**
     * KLASA EllipseMouseHandler - OBSLUGUJE MOUSEEVENT
     */
    public class EllipseMouseHandler implements EventHandler<MouseEvent> {
        NewEllipse newEllipse;
        private double x;
        private double y;

        /**
         * FUNKCJA doMove - ZMIENIA POLOZENIE OBIEKTU
         * @param mouseEvent
         */
        public void doMove(MouseEvent mouseEvent){
            double dx = mouseEvent.getX()-x;
            double dy = mouseEvent.getSceneY()-y;
            if(newEllipse.isHit(x, y)){
                newEllipse.addX(dx);
                newEllipse.addY(dy);
            }
            x += dx;
            y += dy;
        }

        /**
         * FUNKCJA handle - WYWOLUJE ZMIANE POLOZENIA OBIEKTU
         * @param mouseEvent
         */
        @Override
        public void handle(MouseEvent mouseEvent) {
            newEllipse = (NewEllipse) mouseEvent.getSource();

            if(mouseEvent.getEventType()==MouseEvent.MOUSE_CLICKED){
                newEllipse.toFront();
                x = mouseEvent.getX();
                y = mouseEvent.getY();
            }
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount()==2){
                    Stage stage = new Stage();
                    TilePane tilePane = new TilePane();
                    ColorPicker colorPicker = new ColorPicker(Color.RED);
                    Button button = new Button("OK");
                    stage.setTitle("Wybierz kolor");
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            newEllipse.setFill(colorPicker.getValue());
                            stage.hide();
                        }
                    });
                    tilePane.getChildren().add(colorPicker);
                    tilePane.getChildren().add(button);
                    Scene scene = new Scene(tilePane, 300, 300);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            if(mouseEvent.getEventType()==MouseEvent.MOUSE_DRAGGED){
                doMove(mouseEvent);
                newEllipse.toFront();
            }
        }
    }
}
