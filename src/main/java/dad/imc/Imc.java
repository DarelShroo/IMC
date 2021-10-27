package dad.imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class Imc extends Application {
    private final DoubleProperty peso = new SimpleDoubleProperty(63);
    private final DoubleProperty altura = new SimpleDoubleProperty(173);

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        try {
            Label pesoLabel = new Label("Peso: ");
            Label alturaLabel = new Label("Altura: ");
            Label kgLabel = new Label("kg ");
            Label cmLabel = new Label("cm ");
            Label resultadoLabel = new Label();
            Label imcLabel = new Label("IMC: ");
            Label tipoPesoLabel = new Label("Normal");

            TextField pesoTextField = new TextField();
            TextField alturaTextField = new TextField();

            DoubleBinding imc;

            VBox vbox = new VBox(4);
            vbox.setAlignment(Pos.CENTER);
            vbox.setFillWidth(false);

            HBox pesoInput = new HBox(3);
            pesoInput.setAlignment(Pos.CENTER);
            pesoInput.getChildren().addAll(pesoLabel, pesoTextField, kgLabel);

            HBox alturaInput = new HBox(3);
            alturaInput.setAlignment(Pos.CENTER);
            alturaInput.getChildren().addAll(alturaLabel, alturaTextField, cmLabel);


            HBox imcOutput = new HBox(2);
            imcOutput.setAlignment(Pos.CENTER);
            imcOutput.getChildren().addAll(imcLabel, resultadoLabel);

            HBox resultadoOutput = new HBox(1);
            resultadoOutput.setAlignment(Pos.CENTER);
            resultadoOutput.getChildren().addAll(tipoPesoLabel);

            vbox.getChildren().addAll(pesoInput, alturaInput, imcOutput, resultadoOutput);

            Bindings.bindBidirectional(
                    pesoTextField.textProperty(),
                    peso,
                    new NumberStringConverter()
            );

            Bindings.bindBidirectional(
                    alturaTextField.textProperty(),
                    altura,
                    new NumberStringConverter()
            );

            imc = peso.divide(altura.multiply(0.01).multiply(altura.multiply(0.01)));

            resultadoLabel.textProperty().bind(imc.asString());
            imc.addListener((o, ov, nv) -> {
                double result = nv.doubleValue();
                if (result < 18.5f) {
                    tipoPesoLabel.setText("Bajo Peso");
                } else if (result >= 18.5f && result < 25f) {
                    tipoPesoLabel.setText("Normal");
                } else if (result >= 25 && result < 30) {
                    tipoPesoLabel.setText("Sobrepeso");
                } else if (result >= 30) {
                    tipoPesoLabel.setText("Obeso");
                }
            });
            Scene scene = new Scene(vbox, 320, 320);

            primaryStage.setTitle("IMC");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (RuntimeException e) {
            System.out.println("No se pueden usar letras o simbolos");
        }

    }
}
