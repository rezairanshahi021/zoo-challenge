package org.iranshahi.zoochallenge.exceptions;

public final class CategoryMismatchException extends ZooBusinessException {
    public CategoryMismatchException(String roomCategory, String animalCategory) {
        super("Category mismatch [room category:%s, animal category: %s]".formatted(roomCategory, animalCategory), ExceptionCode.CATEGORY_MISMATCH);
    }
}
