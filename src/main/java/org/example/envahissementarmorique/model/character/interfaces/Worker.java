package org.example.envahissementarmorique.model.character.interfaces;

/**
 * Interface for characters capable of working and performing jobs.
 * Workers can perform various tasks related to their profession.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public interface Worker {
    /**
     * Makes the worker perform their job.
     *
     * @return true if the work was successful
     */
    boolean work();

    /**
     * Gets the type of work this worker performs.
     *
     * @return the work type as a string
     */
    String getWorkType();

    /**
     * Makes the worker create or produce something.
     *
     * @param itemName the name of the item to produce
     * @return true if the production was successful
     */
    boolean produce(String itemName);
}
