package dk.eamv.ferrari.sharedcomponents.filter;

import javafx.scene.control.TextField;

// Made by: Mikkel
/**
 * Instances of this class has the capacity to set the filter of a {@code FilteredTable}.
 */
public class FilterTextField<T> extends TextField {

    private final FilteredTableBuilderInfo<T> filteredTableBuilderInfo;

    /**
     * @param filteredTableBuilderInfo interface to pass information about the FilteredTable
     */
    public FilterTextField(FilteredTableBuilderInfo<T> filteredTableBuilderInfo) {
        this.filteredTableBuilderInfo = filteredTableBuilderInfo;
        setPromptText("Filter");
        setupFiltering();
    }

    /**
     * Predicate decides what data is to be shown. In the constructor of the FilteredTable, it is set to true by default
     * and with no conditions.
     * <p>
     * In the method below, the predicate is updated according to an observable value (the input)
     * Whenever the observable input is updated, the oldValue and newValue is saved,
     * and the conditional logic is run again.
     * <p>
     * It first checks if the newValue is null - if this is the case, it is set to an empty string.
     * If newValue is not null, it is converted to lowercase.
     */
    private void setupFiltering() {
        var filteredTable = filteredTableBuilderInfo.getFilteredTable();
        var propertyValueGetters = filteredTableBuilderInfo.getPropertyValueGetters();

        textProperty()
            .addListener((observable, oldValue, newValue) -> {
                var filterText = newValue == null ? "" : newValue.toLowerCase();
                filteredTable.setFilter(item -> {
                    if (filterText.isEmpty()) {
                        return true;
                    }

                    for (var propertyValueGetter : propertyValueGetters) {
                        var propertyValueObject = propertyValueGetter.apply(item);
                        if (propertyValueObject != null) {
                            var propertyValue = propertyValueObject.toString();
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
