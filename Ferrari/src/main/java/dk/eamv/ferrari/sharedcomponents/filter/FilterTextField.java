package dk.eamv.ferrari.sharedcomponents.filter;

import dk.eamv.ferrari.resources.SVGResources;
import javafx.scene.control.TextField;
import javafx.scene.shape.SVGPath;

import java.util.List;
import java.util.function.Function;

/**
 * Lavet af: Mikkel
 */
public class FilterTextField<T> extends TextField {
    private final FilteredTableView<T> filteredTableView;
    private final List<Function<T, Object>> propertyValueGetters;

    public FilterTextField(FilteredTableView<T> filteredTableView, List<Function<T, Object>> propertyValueGetters) {
        this.filteredTableView = filteredTableView;
        this.propertyValueGetters = propertyValueGetters;
        setPromptText("Filter");
        setupFiltering();
    }


    /*
     * Predicate decides what data is to be shown
     * In the constructor of the FilteredTableView, it is set to true by default and with no conditions!
     *
     * In the method below, the predicate is updated according to an observable value (the input)
     * Whenever the observable input is updated, the oldValue and newValue is saved,
     * and the conditional logic is run again.
     *
     * It first checks if the newValue is null - if this is the case, it is set to an empty string
     * If newValue is not null, it is converted to lowercase.
     *
     *
     */
    private void setupFiltering() {
        textProperty().addListener((observable, oldValue, newValue) -> {
            String filterText = newValue == null ? "" : newValue.toLowerCase();
            filteredTableView.setFilter(item -> {
                if (filterText.isEmpty()) {
                    return true;
                }

                for (Function<T, Object> propertyValueGetter : propertyValueGetters) {
                    Object propertyValueObject = propertyValueGetter.apply(item);
                    if (propertyValueObject != null) {
                        String propertyValue = propertyValueObject.toString();
                        if (propertyValue.toLowerCase().contains(filterText)) {
                            return true;
                        }
                    }
                }
                return false;
            });
        });
    }
}
