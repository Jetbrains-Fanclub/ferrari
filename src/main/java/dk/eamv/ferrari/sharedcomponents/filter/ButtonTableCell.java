package dk.eamv.ferrari.sharedcomponents.filter;

import java.util.function.Consumer;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.shape.SVGPath;

// Made by: Mikkel

/**
 * A custom table cell that contains a button.
 * Allows the withButtonColumn method the FilteredTableBuilder class to not also take on the responsibility of
 * creating the ButtonTableCall with an innerclass following the single responsibility principle
 * @see FilteredTableBuilder
 */
public class ButtonTableCell<T> extends TableCell<T, Void> {

    private final Button btn;

    /**
     * Creates a ButtonTableCell with the given button name and action.
     *
     * @param buttonName    the name to display on the button
     * @param onButtonClick the action to perform when the button is clicked
     */
    public ButtonTableCell(String buttonName, Consumer<T> onButtonClick) {
        btn = createButton(buttonName, onButtonClick);
    }

    public ButtonTableCell(Consumer<T> onButtonClick, String svg) {
        btn = createIconButton(svg, onButtonClick);
    }

    /**
     * Creates a button with the given name and action.
     *
     * @param name   the name to display on the button
     * @param action the action to perform when the button is clicked
     * @return the created button
     */
    private Button createButton(String name, Consumer<T> action) {
        var button = new Button(name);
        button.setOnAction(e -> handleButtonClick(action));
        return button;
    }

    /**
     * Creates a button with the given SVG and action.
     *
     * @param svg   the SVG-string to be set as the button graphic
     * @param action the action to perform when the button is clicked
     * @return the created button
     */
    private Button createIconButton(String svg, Consumer<T> action) {
        var button = new Button();
        button.getStyleClass().add("icon-button");

        var icon = new SVGPath();
        icon.setContent(svg);
        icon.setScaleX(1.3);
        icon.setScaleY(1.3);

        button.setGraphic(icon);
        button.setOnAction(e -> handleButtonClick(action));
        return button;
    }

    /**
     * Handles the button click event and performs the given action.
     *
     * @param action the action to perform when the button is clicked
     */
    private void handleButtonClick(Consumer<T> action) {
        var item = getTableView().getItems().get(getIndex());
        action.accept(item);
    }

    /**
     * Updates the item in the cell and sets the graphic based on the cell's state.
     *
     * @param item  the item to update
     * @param empty true if the cell is empty, false otherwise
     */
    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(empty ? null : btn);
    }
}
