package org.example; /**
 * IMPORT BIBLIOTEK
 */
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 * KLASA NewRectangle - OBSLUGUJE OBIEKT Rectangle
 */
public class NewRectangle extends Rectangle {

    /**
     * KONSTRUKTOR NewRectangle - TWORZY OBIEKT I WYWOLUJE EVENTY
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public NewRectangle(double x, double y, double width, double height){
        super(x, y, width, height);
        setOnMouseClicked(new RectangleMouseHandler());
        setOnMouseDragged(new RectangleMouseHandler());
        setOnScroll(new RectangleScrollHandler());
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
    public void addX(double dx){
        this.setX(this.getX()+dx);
    }

    /**
     * FUNKCJA addY - PRZESUWA STWORZONY OBIEKT PO OSI Y
     * @param dy
     */
    public void addY(double dy){
        this.setY(this.getY()+dy);
    }

    /**
     * FUNKCJA addWidth - MODYFIKUJE WYSOKOSC OBIEKTU
     * @param w
     */
    public void addWidth(double w){
        this.setWidth(this.getWidth()*(1.0+w));
    }

    /**
     * FUNKCJA addHeight - MODYFIKUJE SZEROKOSC OBIEKTU
     * @param h
     */
    public void addHeight(double h){
        this.setHeight(this.getHeight()*(1.0+h));
    }

    /**
     * KLASA RectangleMouseHandler - OBSLUGUJE MOUSEEVENT
     */
    public class RectangleMouseHandler implements EventHandler<MouseEvent>{
        NewRectangle newRectangle;
        private double x;
        private double y;

        /**
         * FUNKCJA doMove - ZMIENIA POLOZENIE OBIEKTU
         * @param mouseEvent
         */
        public void doMove(MouseEvent mouseEvent){
            double dx = mouseEvent.getX()-x;
            double dy = mouseEvent.getY()-y;
            if(newRectangle.isHit(x, y)){
                newRectangle.addX(dx);
                newRectangle.addY(dy);
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
            newRectangle = (NewRectangle) mouseEvent.getSource();

            /**
             * BLOK IF NR 1 - WYWOLUJE SIE JESLI NACISNIEMY NA OBIEKT
             */
            if(mouseEvent.getEventType()==MouseEvent.MOUSE_CLICKED){
                newRectangle.toFront();
                x = mouseEvent.getX();
                y = mouseEvent.getY();
            }

            /**
             * BLOK IF NR 2 - WYWOLUJE SIE JESLI NACISNIEMY NA OBIEKT DWUKROTNIE LEWYM PRZYCISKIEM MYSZY
             */
            if(mouseEvent.getButton()== MouseButton.PRIMARY){
                if(mouseEvent.getClickCount()==2){

                    /**
                     * INICJACJA ZMIENNYCH
                     */
                    Stage stage = new Stage();
                    TilePane tilePane = new TilePane();
                    Button button = new Button("OK");
                    ColorPicker colorPicker = new ColorPicker(Color.RED);

                    /**
                     * OBSLUGA EVENTOW NA GUZIKU BUTTON
                     */
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            newRectangle.setFill(colorPicker.getValue());
                            stage.hide();
                        }
                    });

                    /**
                     * UTWORZENIE SLIDERA
                     */
                    Slider slider = new Slider(0, 90, 0);
                    slider.setShowTickLabels(true);
                    slider.setShowTickMarks(true);
                    slider.setMajorTickUnit(30);
                    slider.setBlockIncrement(10);
                    slider.setOrientation(Orientation.HORIZONTAL);
                    slider.setLayoutX(2);
                    slider.setLayoutY(195);
                    Rotate rotate = new Rotate();
                    rotate.setPivotX(newRectangle.getX()+newRectangle.getWidth()/2);
                    rotate.setPivotY(newRectangle.getY()+newRectangle.getHeight()/2);
                    newRectangle.getTransforms().addAll(rotate);

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
                    newRectangle.getTransforms().add(rotate);
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
                newRectangle.toFront();
            }
        }
    }

    /**
     * KLASA PolygonScrollHandler - OBSLUGUJE SCROLLEVENT
     */
    public class RectangleScrollHandler implements EventHandler<ScrollEvent>{
        NewRectangle rectangle;

        /**
         * FUNKCJA doMove - ZMIENIA ROZMIAR OBIEKTU
         * @param scrollEvent
         */
        public void doScale(ScrollEvent scrollEvent){
            double x = scrollEvent.getX();
            double y = scrollEvent.getY();
            if(rectangle.isHit(x, y)){
                rectangle.addWidth(scrollEvent.getDeltaY()*0.001);
                rectangle.addHeight(scrollEvent.getDeltaY()*0.001);
            }
        }

        /**
         * FUNKCJA handle - ROZPATRUJE NAPOTKANY EVENT
         * @param scrollEvent
         */
        @Override
        public void handle(ScrollEvent scrollEvent) {
            rectangle = (NewRectangle) scrollEvent.getSource();
            if(scrollEvent.getEventType()==ScrollEvent.SCROLL){
                doScale(scrollEvent);
            }
        }
    }
}
