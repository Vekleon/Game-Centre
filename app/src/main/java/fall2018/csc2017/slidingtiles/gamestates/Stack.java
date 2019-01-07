package fall2018.csc2017.slidingtiles.gamestates;

/**
 * A Stack interface
 *
 * @param <T> The desired type of object to be used
 */
interface Stack<T> {
    /**
     * Used to put into into an array/arraylist
     *
     * @param t the item that will be added into the stack
     */
    void push(T t);

    /**
     * Takes the last item that was added to the stack
     *
     * @return the item that was last added to the stack
     */
    T pop();

    /**
     * Determines whether the stack is empty or not
     *
     * @return whether the stack is empty or not
     */
    boolean isEmpty();
}
