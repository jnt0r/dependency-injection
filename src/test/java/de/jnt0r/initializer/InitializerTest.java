package de.jnt0r.initializer;

import componentScanTestPackage.FirstComponent;
import componentScanTestPackage.SecondComponent;
import org.junit.jupiter.api.Test;
import uninitializedComponentTestPackage.TestApplication;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class InitializerTest {

    @Test
    public void testComponentScan() {
        Set<Class<?>> expected = new HashSet<>(Arrays.asList(FirstComponent.class, SecondComponent.class));
        Set<Class<?>> components = Initializer.componentScan(Package.getPackage("componentScanTestPackage"));

        assertEquals(expected, components);
    }

    @Test
    public void testAutowiringNonComponentClassThrowsException() {
        assertThrows(RuntimeException.class, () -> Initializer.initialize(TestApplication.class, new String[] {}));
    }

}
