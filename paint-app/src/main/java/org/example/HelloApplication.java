package org.example; /**
 * IMPORT BIBLIOTEK
 */
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * KLASA HelloApplication - TWORZY CALY PROGRAM
 */
public class HelloApplication extends Application {

    /**
     * DEKLARACJA ZMIENNYCH
     */
    private static Scene scene;
    private static BorderPane borderPane;
    private static MenuBar menuBar;
    private static Menu plikMenu, figuryMenu;
    private static MenuItem trojkatMenuItem, prostokatMenuItem, okragMenuItem, instrukcjaMenuItem, informacjeMenuItem;
    private static Pane pane;
    private static Dialog<String> dialogInstrukcja, dialogInformacje;
    private static ButtonType informacjeButton, instrukcjaButton;
    private double width;
    private double height;

    /**
     * FUNKCJA start - TWORZY PROGRAM
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        /**
         * ZAINICJOWANIE ZMIENNYCH
         */
        borderPane = new BorderPane();
        pane = new Pane();
        menuBar = new MenuBar();
        plikMenu = new Menu("Plik");
        figuryMenu = new Menu("Figury");
        informacjeMenuItem = new MenuItem("Informacje");
        instrukcjaMenuItem = new MenuItem("Instrukcja");
        trojkatMenuItem = new MenuItem("Trojkat");
        prostokatMenuItem = new MenuItem("Prostokat");
        okragMenuItem = new MenuItem("Okrag");
        width = Screen.getPrimary().getBounds().getWidth();
        height = Screen.getPrimary().getBounds().getHeight();

        /**
         * INICJACJA EVENT HANDLERA DLA TROJKATA
         */
        trojkatMenuItem.setOnAction(new EventHandler<ActionEvent>() {

            /**
             * FUNKCJA handle - WYWOLUJE ZCZYTYWANIE PUNKTOW Z pane
             * @param actionEvent
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<Double> list = new ArrayList<Double>();

                /**
                 * KLASA wierzcholekEventHandler - ZCZYTUJE 3 WIERZCHOLKI TROJKATA I TWORZY polygon
                 */
                class wierzcholekEventHandler implements EventHandler<MouseEvent>{
                    double x, y;

                    /**
                     * FUNKCJA handle - ZCZYTUJE 3 WIERZCHOLKI TROJKATA
                     * @param mouseEvent
                     */
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(mouseEvent.getEventType()==MouseEvent.MOUSE_CLICKED && list.size()!=6){
                            x = mouseEvent.getX();
                            y = mouseEvent.getY();
                            list.add(x);
                            list.add(y);
                        }
                        if(list.size()==6){
                            NewPolygon newPolygon = new NewPolygon(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5));
                            newPolygon.setFill(Color.RED);
                            newPolygon.setStrokeWidth(2);
                            newPolygon.setStroke(Color.BLACK);
                            pane.getChildren().add(newPolygon);
                            pane.setOnMouseClicked(null);
                        }
                    }
                }

                /**
                 * WYWOLUJE wierzcholekEventHandler
                 */
                pane.setOnMouseClicked(new wierzcholekEventHandler());
                menuBar.toFront();
            }
        });

        /**
         * INICJACJA EVENT HANDLERA DLA OKREGU
         */
        okragMenuItem.setOnAction(new EventHandler<ActionEvent>() {

            /**
             * FUNKCJA handle - WYWOLUJE UTWORZENIE NOWEGO OKREGU
             * @param actionEvent
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<Double> list = new ArrayList<Double>();

                /**
                 * KLASA wierzcholekEventHandler - POZYSKUJE SRODEK OKREGU PO NACISNIECIU MYSZA I TWORZY OKRAG
                 */
                class srodekEventHandler implements EventHandler<MouseEvent>{
                    Double x, y;

                    /**
                     * FUNKJCA handle - ZAPISUJE SRODEK OKREGU DO list
                     * @param mouseEvent
                     */
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(mouseEvent.getEventType()==MouseEvent.MOUSE_CLICKED && list.size()!=2){
                            x = mouseEvent.getX();
                            y = mouseEvent.getY();
                            list.add(x);
                            list.add(y);
                        }
                        if(list.size()==2){
                            NewEllipse newEllipse = new NewEllipse(list.get(0), list.get(1), 100, 100);
                            newEllipse.setFill(Color.RED);
                            newEllipse.setStrokeWidth(2);
                            newEllipse.setStroke(Color.BLACK);
                            pane.getChildren().add(newEllipse);
                            pane.setOnMouseClicked(null);
                        }
                    }
                }

                /**
                 * UTWORZENIE OBIEKTU KLASY wierzcholekEventHandler
                 */
                pane.setOnMouseClicked(new srodekEventHandler());
                menuBar.toFront();
            }
        });

        /**
         * INICJACJA EVENT HANDLERA DLA prostokatMenuItem
         */
        prostokatMenuItem.setOnAction(new EventHandler<ActionEvent>() {

            /**
             * FUNKCJA handle - WYWOLUJE UTWORZENIE NOWEGO PROSTOKATA
             * @param actionEvent
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<Double> list = new ArrayList<Double>();

                /**
                 * KLASA WierzcholekEventHandler - WYLAPUJE WIERZCHOLKI PROSTOKATA I TWORZY NOWY OBIEKT
                 */
                class WierzcholekEventHandler implements EventHandler<MouseEvent>{

                    /**
                     * FUNKCJA handle - WYLAPUJE WIERZCHOLKI PROSTOKATA
                     * @param mouseEvent
                     */
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(mouseEvent.getEventType()==MouseEvent.MOUSE_CLICKED && list.size()!=4){
                            list.add(mouseEvent.getX());
                            list.add(mouseEvent.getY());
                        }
                        if(list.size()==4){
                            NewRectangle rectangle = new NewRectangle(list.get(0), list.get(1), Math.abs(list.get(2)-list.get(0)), Math.abs(list.get(3)- list.get(1)));
                            rectangle.setFill(Color.RED);
                            rectangle.setStrokeWidth(2);
                            rectangle.setStroke(Color.BLACK);
                            pane.getChildren().add(rectangle);
                            pane.setOnMouseClicked(null);
                        }
                    }
                }
                pane.setOnMouseClicked(new WierzcholekEventHandler());
                menuBar.toFront();
            }
        });

        /**
         * INICJACJA EVENT HANDLERA DLA informacjeMenuItem
         */
        informacjeMenuItem.setOnAction(new EventHandler<ActionEvent>() {

            /**
             * FUNKCJA handle - TWORZY NOWY DIALOG ZAWIERAJACY INFORMACJE I GO WYSWIETLA
             * @param actionEvent
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                dialogInformacje = new Dialog<String>();
                informacjeButton = new ButtonType("OK");
                dialogInformacje.setTitle("Informacje");
                dialogInformacje.setContentText("Autor: Denis Stocki\n\nProgram: KP - Lista 5\n\nPrzeznaczenie: Tworzenie figur geometrycznych");
                dialogInformacje.getDialogPane().getButtonTypes().add(informacjeButton);
                dialogInformacje.showAndWait();
            }
        });

        /**
         * INICJACJA EVENT HANDLERA DLA instrukcjaMenuItem
         */
        instrukcjaMenuItem.setOnAction(new EventHandler<ActionEvent>() {

            /**
             * FUNKCJA handle - TWORZY NOWY DIALOG INSTRUKCJA I GO WYSWIETLA
             * @param actionEvent
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                dialogInstrukcja = new Dialog<String>();
                instrukcjaButton = new ButtonType("OK");
                dialogInstrukcja.setTitle("Instrukcja");
                dialogInstrukcja.setContentText("INSTRUKCJA\n\n1. Aby uzyskac informacje na temat tego programu wejdz do struktury PLIK -> INFORMACJE." +
                        "\n\n2. W strukturze FIGURY mozesz wybierac figury ktore chcesz dodawac do programu."+"\n\n3. Aby dodac OKRAG przejdz do FIGURY -> OKRAG" +
                        ", a nastepnie nacisnij dowolne miejsce na ekranie, w ktorym znajdzie sie srodek okregu."+
                        "\n\n4. Aby dodac PROSTOKAT przejdz do FIGURY -> PROSTOKAT, a nastepnie nacisnij 2 dowolne punkty na ekranie bedace wierzcholkami prostokata." +
                                "\n\n5. Aby dodac TROJKAT przejdz do FIGURY -> TROJKAT, a nastepnie nacisnij 3 dowolne punkty na ekranie bedace wierzcholkami trojkata." +
                        "\n\n6. Aby zmienic rozmiar dodanej figury najedz myszka na niego i uzyj SCROLLA." +
                        "\n\n7. Aby zmienic pozycje dodanej figury najedz myszka na niego przytrzymaj lewy przycisk myszy i przesun." +
                        "\n\n8. Po dwukrotnym nacisnieciu lewym przyciskiem myszy na dana figure otworzy sie dodatkowe menu dla danej figury." +
                        "\n\n9. W tym menu mozesz wybrac kolor danej figury a nastepnie zatwierdzic dany kolor guzikiem OK lub zrotowac dana figure uzywajac SLIDERA.");
                dialogInstrukcja.getDialogPane().getButtonTypes().add(instrukcjaButton);
                dialogInstrukcja.showAndWait();
            }
        });

        /**
         * ROZBUDOWA menuBar
         */
        figuryMenu.getItems().add(trojkatMenuItem);
        figuryMenu.getItems().add(prostokatMenuItem);
        figuryMenu.getItems().add(okragMenuItem);
        plikMenu.getItems().add(informacjeMenuItem);
        plikMenu.getItems().add(instrukcjaMenuItem);
        menuBar.getMenus().add(plikMenu);
        menuBar.getMenus().add(figuryMenu);

        /**
         * USTAWIENIE KOLOROW BACKGROUND
         */
        menuBar.setBackground(Background.fill(Color.web("#ffe6cc")));
        pane.setBackground(Background.fill(new LinearGradient(
                0, 0, 1, 1, true,                      //sizing
                CycleMethod.NO_CYCLE,                  //cycling
                new Stop(0, Color.web("#81c483")),     //colors
                new Stop(1, Color.web("#fcc200")))));

        /**
         * ROZBUDOWA borderPane
         */
        borderPane.setTop(menuBar);
        borderPane.setCenter(pane);

        /**
         * DODANIE SCENY DO stage I WYSWIETLENIE stage
         */
        scene = new Scene(borderPane, width, height);
        stage.setScene(scene);
		stage.setFullScreen(true);
        stage.show();
    }

    /**
     * URUCHAMIA PROGRAM
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}