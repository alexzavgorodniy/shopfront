package exception;

public enum ErrorMessage {
    NO_ITEM("There is no item with such ID!"),
    CATEGORY_CANNOT_BE_DELETED("The category cannot be deleted, because there are products with this category!"),
    MANUFACTURER_CANNOT_BE_DELETED("The manufacturer cannot be deleted, because there are products with this manufacturer!");

    private String message;

    ErrorMessage() {
    }

    ErrorMessage(String message) {
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
