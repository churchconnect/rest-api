package co.sharptop.church

import java.lang.reflect.Field
import java.lang.reflect.Modifier

class ClassUtil {

    /**
     * returns the properties actually written in the code file of the specified class.
     * Ignores any static properties.
     * This is similar to getting just the persisted properties of a domain class.
     * @param clazz
     * @return
     */
    static List<Field> getTrueFields(Class clazz) {
        clazz.declaredFields.findAll {
            !it.synthetic && !Modifier.isStatic(it.modifiers)
        }
    }

    /**
     * recurses up the inheritance tree to get true fields from parent classes to the depth specified.
     * Depth of 0 is equivalent to calling getTrueFields without a depth.
     * For example, if class B extends A, and I call getTrueFields(B, 1), I will get the fields of B and A.
     * @param clazz
     * @param depth
     * @return
     */
    static List<Field> getTrueFields(Class clazz, Integer depth) {
        if (!depth) {
            return getTrueFields(clazz)
        }

        return getTrueFields(clazz) + getTrueFields(clazz.superclass, depth - 1)
    }

}
