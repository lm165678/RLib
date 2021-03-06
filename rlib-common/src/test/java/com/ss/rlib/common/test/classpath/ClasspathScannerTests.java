package com.ss.rlib.common.test.classpath;

import com.ss.rlib.common.classpath.ClassPathScannerFactory;
import com.ss.rlib.common.util.array.impl.AbstractArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

/**
 * @author JavaSaBr
 */
public class ClasspathScannerTests {

    @Test
    void testSystemClasspathScanner() {

        var scanner = ClassPathScannerFactory.newDefaultScanner();
        scanner.setUseSystemClasspath(true);
        scanner.scan();

        var implementations = scanner.findImplements(Collection.class);

        Assertions.assertFalse(implementations.isEmpty());

        var inherited = scanner.findInherited(AbstractArray.class);

        Assertions.assertFalse(inherited.isEmpty());
    }
}
