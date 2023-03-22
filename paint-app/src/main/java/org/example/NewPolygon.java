package org.example; /**
 * IMPORT BIBLIOTEK
 */
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * KLASA NewPolygon - OBSLUGUJE OBIEKT POLYGON
 */
public class NewPolygon extends Polygon {

    /**
     * KONSTRUKTOR NewPolygon - TWORZY OBIEKT I WYWOLUJE EVENTY
     * @param a1
     * @param a2
     * @param b1
     * @param b2
     * @param c1
     * @param c2
     */
    public NewPolygon(double a1, double a2, double b1, double b2, double c1, double c2){
        super(a1, a2, b1, b2, c1, c2);
        setOnMouseClicked(new PolygonMouseHandler());
        setOnMouseDragged(new PolygonMouseHandler());
        setOnScroll(new ScrollEventHandler());
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
     * @param dx
     */
    public void addX(double dx) {
        ObservableList<Double> list = this.getPoints();
        for (int i = 0; i <= 2; i++) {
            list.set(2 * i, list.get(2 * i) + dx);
        }
    }

    /**
     * FUNKCJA addY - PRZESUWA STWORZONY OBIEKT PO OSI Y
     * @param dy
     */
    public void addY(double dy){
        ObservableList<Double> list = this.getPoints();
        for(int i=0; i<=2; i++){
            list.set(2*i+1, list.get(2*i+1)+dy);
        }
    }

    /**
     * FUNKCJA changeSize - ZMIENIA ROZMIAR OBIEKTU
     * @param dx
     */
    public void changeSize(double dx){
        double barycentrumX, barycentrumY;
        barycentrumX = findBarycentrumX();
        barycentrumY = findBarycentrumY();
        changePoint(0, 1, barycentrumX, barycentrumY, dx);
        changePoint(2, 3, barycentrumX, barycentrumY, dx);
        changePoint(4, 5, barycentrumX, barycentrumY, dx);
    }

    /**
     * FUNKCJA changePoint - ZMIENIA POLOZENIE JEDNEGO Z WIERZCHOLKOW OBIEKTU
     * @param x
     * @param y
     * @param brX
     * @param brY
     * @param dx
     */
    public void changePoint(int x, int y, double brX, double brY, double dx){
        ObservableList<Double> list = this.getPoints();
        double lengthX, lengthY;
        lengthX = brX - list.get(x);
        lengthY = brY - list.get(y);
        list.set(x, list.get(x)-dx*lengthX);
        list.set(y, list.get(y)-dx*lengthY);
    }

    /**
     * ZNAJDUJE WSPOLRZEDNA X BARYCENTRUM
     * @return
     */
    public double findBarycentrumX(){
        ObservableList<Double> list = this.getPoints();
        return (list.get(0)+list.get(2)+list.get(4))/3;
    }

    /**
     * ZNAJDUJE WSPOLRZEDNA Y BARYCENTRUM
     * @return
     */
    public double findBarycentrumY(){
        ObservableList<Double> list = this.getPoints();
        return (list.get(1)+list.get(3)+list.get(5))/3;
    }

    /**
     * KLASA EllipseScrollHandler - OBSLUGUJE SCROLLEVENT
     */
    public class ScrollEventHandler implements EventHandler<ScrollEvent>{
        NewPolygon newPolygon;
        private double dx;

        /**
         * FUNKCJA doScale - WYWOLUJE ZMIANE POLOZENIA JEDNEGO Z WIERZCHOLKOW OBIEKTU
         * @param scrollEvent
         */
        public void doScale(ScrollEvent scrollEvent){
            double x = scrollEvent.getX();
            double y = scrollEvent.getY();
            if(newPolygon.isHit(x, y)) {
                newPolygon.changeSize(scrollEvent.getDeltaY() * 0.0006);
            }
        }

        /**
         * FUNKCJA handle - ROZPATRUJE NAPOTKANY EVENT
         * @param scrollEvent
         */
        @Override
        public void handle(ScrollEvent scrollEvent) {
            newPolygon = (NewPolygon) scrollEvent.getSource();
            if(scrollEvent.getEventType()==ScrollEvent.SCROLL){
                doScale(scrollEvent);
            }
        }
    }

    /**
     * KLASA PolygonMouseHandler - OBSLUGUJE MOUSEEVENT
     */
    public class PolygonMouseHandler implements EventHandler<MouseEvent>{
        NewPolygon newPolygon;
        private double x;
        private double y;

        /**
         * FUNKCJA doMove - ZMIENIA POLOZENIE OBIEKTU
         * @param event
         */
        public void doMove(MouseEvent event){
            double dx = event.getX()-x;
            double dy = event.getY()-y;
            if(newPolygon.isHit(x, y)){
                newPolygon.addX(dx);
                newPolygon.addY(dy);
            }
            x += dx;
            y += dy;
        }

        /**
         * FUNKCJA handle - ROZPATRUJE NAPOTKANY EVENT
         * @param mouseEvent
         */
        @Override
        public void handle(MouseEvent mouseEvent) {
            newPolygon = (NewPolygon) mouseEvent.getSource();

            /**
             * BLOK IF NR 1 - WYWOLUJE SIE JESLI NACISNIEMY NA OBIEKT
             */
            if(mouseEvent.getEventType()==MouseEvent.MOUSE_CLICKED){
                newPolygon.toFront();
                x = mouseEvent.getX();
                y = mouseEvent.getY();
            }

            /**
             * BLOK IF NR 2 - WYWOLUJE SIE JESLI NACISNIEMY NA OBIEKT LEWYM PRZYCISKIEM DWA RAZY
             */
            if(mouseEvent.getButton()== MouseButton.PRIMARY){
                if(mouseEvent.getClickCount()==2){

                    /**
                     * INICJACJA ZMIENNYCH
                     */
                    Stage stage = new Stage();
                    TilePane tilePane = new TilePane();
                    ColorPicker colorPicker = new ColorPicker(Color.RED);
                    Button button = new Button("OK");

                    /**
                     * OBSLUGA EVENTOW NA GUZIKU BUTTON
                     */
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            newPolygon.setFill(colorPicker.getValue());
                            stage.hide();
                        }
                    });

                    /**
                     * UTWORZENIE SLIDERA
                     */
                    Slider slider = new Slider(0, 180, 0);
                    slider.setShowTickLabels(true);
                    slider.setShowTickMarks(true);
                    slider.setMajorTickUnit(45);
                    slider.setBlockIncrement(10);
                    slider.setOrientation(Orientation.HORIZONTAL);
                    slider.setLayoutX(2);
                    slider.setLayoutY(195);
                    Rotate rotate = new Rotate();
                    rotate.setPivotX(newPolygon.findBarycentrumX());
                    rotate.setPivotY((newPolygon.findBarycentrumY()));
                    newPolygon.getTransforms().addAll(rotate);

                    /**
                     * OBSLUGA EVENTOW SLIDERA
                     */
                    slider.valueProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                            rotate.setAngle((double) t1);
                        }
                    });

                    /**
                     * DODANIE OPCJI PRZESUWANIA DO PROGRAMU
                     */
                    newPolygon.getTransforms().add(rotate);
                    stage.setTitle("Wybierz kolor");
                    tilePane.getChildren().add(colorPicker);
                    tilePane.getChildren().add(button);
                    tilePane.getChildren().add(slider);
                    Scene scene = new Scene(tilePane, 300, 300);
                    stage.setScene(scene);
                    stage.show();
                }
            }

            /**
             * BLOK IF NR 3 - WYWOLUJE SIE JESLI PRZESUNIEMY OBIEKT
             */
            if(mouseEvent.getEventType()==MouseEvent.MOUSE_DRAGGED){
                doMove(mouseEvent);
                newPolygon.toFront();
            }
        }
    }
}
