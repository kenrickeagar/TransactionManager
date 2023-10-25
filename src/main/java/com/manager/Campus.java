package com.manager;

/**
 * Define Campus enum class.
 * @author Kenrick Eagar, Zachary Derish
 */

public enum Campus {
    NEW_BRUNSWICK (0),
    NEWARK (1),
    CAMDEN (2);

    private final int campusCode;

    /**
     * Given campus code, will return corresponding enum Campus object.
     * @param campusCode integer representing campus code.
     */
    Campus(int campusCode) {
        this.campusCode = campusCode;
    }

    /**
     * Getter method to obtain campus code from Campus object
     * @return integer representing campus code
     */
    public int getCampusCode() {
        return this.campusCode;
    }
}
