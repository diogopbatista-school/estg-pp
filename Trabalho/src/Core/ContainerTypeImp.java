package Core;

import Exceptions.ContainerTypeException;
import Io.ImporterImp;
import com.estg.core.ContainerType;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

/*
* Nome: Diogo Pereira Batista
* Número: 8230367
* Turma: LSIRC T1
*
* Nome: Rúben da Silva Uth
* Número: 8210481
* Turma: LSIRC T2
 */
/**
 *
 * The ContainerTypeImp class provides functionalities to manage a static array
 * of types and also the number of types. Provides aswell the string of the
 * ContainerType
 *
 * @author Diogo e Ruben
 */
public class ContainerTypeImp implements ContainerType {

    /**
     * The value to start arrays
     */
    private static int INICIALIZE_ARRAY = 10;

    /**
     * The value for expanding arrays dynamically
     */
    private static int EXPAND_ARRAY = 2;

    /**
     * The static array of types
     */
    private static String[] types = new String[INICIALIZE_ARRAY];

    /**
     * The static number of types available
     */
    private static int nTypes = 0;

    /**
     * The type
     */
    private String type;

    /**
     * Constructs a new container type with the specified type. This method also
     * import automatic all the types from the API if the class doesnt have any
     * type inside the static array
     *
     * @param type - The name of the type
     */
    public ContainerTypeImp(String type) {
        if (nTypes == 0) {
            try {
                ContainerTypeImp.importTypes();
            } catch (ParseException ex) {
                Logger.getLogger(ContainerTypeImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.type = "Unknown";

        for (int i = 0; i < nTypes; i++) {
            if (type.equals(types[i])) {
                this.type = type;
                break;
            }
        }

    }

    /**
     * Getter for the String type
     *
     * @return - The String type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Compares this object to the specified object for equality.
     *
     * @param obj the object to be compared for equality with this object
     * @return {@code true} if the specified object is equal to this object;
     * {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContainerTypeImp other = (ContainerTypeImp) obj;
        return Objects.equals(this.type, other.type);
    }

    /**
     * Getter for static array of types
     *
     * @return - All the types available
     */
    public static String[] getTypes() {
        String[] aux = new String[nTypes];
        int count = 0;

        for (int i = 0; i < nTypes; i++) {
            if (types[i] != null) {
                aux[count] = types[i];
                count++;
            }
        }

        return aux;
    }

    /**
     * This method checks if the object i want to compare is in the array or not
     *
     * @param array - The array of object i wanna check for
     * @param element - The object I want to check whether or not it is in the
     * array
     * @param size - The number of objects I will check
     * @return - True if the exact same object is in the array . False otherwise
     */
    private static boolean containsElement(Object[] array, Object element, int size) {
        for (int i = 0; i < size; i++) {
            if (element.equals(array[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if the type is already in the class
     *
     * @param type - The type i wanna check if it already exists in the class
     * @return - True if the exact same type is in the array . False otherwise
     */
    private static boolean hasSameType(String type) {
        return containsElement(types, type, nTypes);
    }

    /**
     * This method expands the size of the array of objects
     *
     * @param object - The object array i want to expand.
     * @param size - The size of the array object .
     * @param newObject - The new array with new size
     * @return - The expanded array with all previous informations inside
     */
    private static Object[] expandArray(Object[] object, int size, Object[] newObject) {
        for (int i = 0; i < size; i++) {
            newObject[i] = object[i];
        }
        object = newObject;
        return object;
    }

    /**
     * This method expands the size of the array of types
     */
    private static void expandTypeArray() {
        Object[] aux = new String[nTypes * EXPAND_ARRAY];
        aux = expandArray(types, nTypes, aux);
        types = (String[]) aux;
    }

    /**
     * This static method a new type to the class
     *
     * @param type - The new type
     * @throws ContainerTypeException - if type is null if already exists at the
     * array
     */
    public static void addType(String type) throws ContainerTypeException {
        if (type == null) {
            throw new ContainerTypeException("String type cannot be null");
        }

        if (hasSameType(type)) {
            throw new ContainerTypeException("Container Type already exists");
        }

        if (types.length == nTypes) {
            expandTypeArray();
        }

        types[nTypes++] = type;

    }

    /**
     * This method imports container types using the ImporterImp class.
     *
     * @throws ParseException if an error occurs during the parsing process
     */
    private static void importTypes() throws ParseException {
        ImporterImp imp = new ImporterImp("WEB");
        ((ImporterImp) imp).importContainerTypesWebApi();
    }
}
